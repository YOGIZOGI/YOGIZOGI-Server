package dev.yogizogi.domain.auth.service;

import static dev.yogizogi.global.common.model.constant.Number.COOLSMS_SUCCESS_CODE;
import static dev.yogizogi.global.common.model.constant.Format.TOKEN_PREFIX;

import dev.yogizogi.domain.auth.exception.AuthException;
import dev.yogizogi.domain.auth.model.dto.request.LoginInDto;
import dev.yogizogi.domain.auth.model.dto.response.LoginOutDto;
import dev.yogizogi.domain.auth.model.dto.response.SendVerificationCodeOutDto;
import dev.yogizogi.domain.auth.model.dto.response.VerifyCodeOutDto;
import dev.yogizogi.domain.member.exception.MemberException;
import dev.yogizogi.domain.member.model.entity.Member;
import dev.yogizogi.domain.member.repository.MemberRepository;
import dev.yogizogi.domain.security.service.JwtService;
import dev.yogizogi.global.common.code.ErrorCode;
import dev.yogizogi.global.common.status.MessageStatus;
import dev.yogizogi.global.common.status.VerificationStatus;
import dev.yogizogi.global.util.CodeUtils;
import dev.yogizogi.global.util.RedisUtils;
import dev.yogizogi.global.util.SmsUtils;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;

    private final JwtService jwtService;

    private final PasswordEncoder passwordEncoder;
    private final SmsUtils smsUtils;
    private final RedisUtils redisUtils;

    @Transactional(readOnly = true)
    public SendVerificationCodeOutDto sendVerificationCode(String phoneNumber) {
  
        if (!memberRepository.findByPhoneNumber(phoneNumber).isEmpty()) {
            throw new MemberException(ErrorCode.DUPLICATE_PHONE_NUMBER);
        }

        SingleMessageSentResponse result = smsUtils.sendOne(phoneNumber, CodeUtils.verification());
        MessageStatus status = checkSentSuccessfully(result);

        return SendVerificationCodeOutDto.of(status, result.getStatusMessage());

    }

    private static MessageStatus checkSentSuccessfully(SingleMessageSentResponse result) {

        if (!COOLSMS_SUCCESS_CODE.contains(result.getStatusCode())) {
            return MessageStatus.FAIL;
        }

        return MessageStatus.SUCCESS;
    }

    @Transactional(readOnly = true)
    public VerifyCodeOutDto checkVerificationCode(String phoneNumber, String code) {

        VerificationStatus status = VerificationStatus.PASS;
        String findCode = redisUtils.findByKey(phoneNumber);

        if (!Objects.equals(findCode, code)) {
            status = VerificationStatus.NOT_PASS;
        }

        return VerifyCodeOutDto.of(status, phoneNumber);

    }

    @Transactional(readOnly = true)
    public LoginOutDto login(LoginInDto res) throws AuthException {

        Member findMember = memberRepository.findByAccountName(res.getAccountName())
                .orElseThrow(() -> new AuthException(ErrorCode.NOT_EXIST_ACCOUNT));

        if (!passwordEncoder.matches(
                res.getPassword(), findMember.getPassword()
        )) {
            throw new AuthException(ErrorCode.FAIL_TO_LOGIN);
        }

        return LoginOutDto.of(
                findMember.getId(),
                findMember.getAccountName(),
                jwtService.createAccessToken(findMember),
                jwtService.createRefreshToken(findMember)
        );

    }

}
