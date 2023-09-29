package dev.yogizogi.domain.auth.service;

import static dev.yogizogi.global.common.model.constant.Number.COOLSMS_SUCCESS_CODE;

import dev.yogizogi.domain.auth.exception.AuthException;
import dev.yogizogi.domain.auth.exception.FailLoginException;
import dev.yogizogi.domain.auth.model.dto.request.LoginInDto;
import dev.yogizogi.domain.auth.model.dto.response.LoginOutDto;
import dev.yogizogi.domain.auth.model.dto.response.SendVerificationCodeOutDto;
import dev.yogizogi.domain.auth.model.dto.response.VerifyCodeOutDto;
import dev.yogizogi.domain.user.exception.DuplicatedPhoneNumberException;
import dev.yogizogi.domain.user.exception.NotExistAccountException;
import dev.yogizogi.domain.user.model.entity.User;
import dev.yogizogi.domain.user.repository.UserRepository;
import dev.yogizogi.domain.security.service.JwtService;
import dev.yogizogi.domain.user.service.UserService;
import dev.yogizogi.global.common.code.ErrorCode;
import dev.yogizogi.global.common.status.BaseStatus;
import dev.yogizogi.global.common.status.MessageStatus;
import dev.yogizogi.global.common.status.VerificationStatus;
import dev.yogizogi.global.util.CodeUtils;
import dev.yogizogi.global.util.RedisUtils;
import dev.yogizogi.infra.coolsms.CoolSmsService;
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

    private final UserRepository userRepository;

    private final UserService userService;
    private final JwtService jwtService;

    private final PasswordEncoder passwordEncoder;
    private final CoolSmsService coolSmsService;
    private final RedisUtils redisUtils;

    @Transactional(readOnly = true)
    public SendVerificationCodeOutDto sendVerificationCode(String phoneNumber) {

        if (userService.checkPhoneNumberDuplication(phoneNumber)) {
            throw new DuplicatedPhoneNumberException(ErrorCode.DUPLICATE_PHONE_NUMBER);
        }

        SingleMessageSentResponse result = coolSmsService.sendOne(phoneNumber, CodeUtils.verification());
        MessageStatus status = checkSentSuccessfully(result);

        return SendVerificationCodeOutDto.of(status, result.getStatusMessage());

    }

    private MessageStatus checkSentSuccessfully(SingleMessageSentResponse result) {

        if (!COOLSMS_SUCCESS_CODE.contains(result.getStatusCode())) {
            return MessageStatus.FAIL;
        }

        return MessageStatus.SUCCESS;

    }

    @Transactional(readOnly = true)
    public VerifyCodeOutDto checkVerificationCode(String phoneNumber, String code) {
        String findCode = redisUtils.findByKey(phoneNumber);
        VerificationStatus status = isValidVerificationCode(code, findCode);
        return VerifyCodeOutDto.of(status, phoneNumber);
    }

    private VerificationStatus isValidVerificationCode(String code, String findCode) {
        if (!Objects.equals(findCode, code)) {
            return VerificationStatus.NOT_PASS;
        }
        return VerificationStatus.PASS;
    }

    @Transactional(readOnly = true)
    public LoginOutDto login(LoginInDto req) throws AuthException {

        User findUser = userRepository.findByAccountNameAndStatus(req.getAccountName(), BaseStatus.ACTIVE)
                .orElseThrow(() -> new NotExistAccountException(ErrorCode.NOT_EXIST_ACCOUNT));

        if (!passwordEncoder.matches(
                req.getPassword(), findUser.getPassword()
        )) {
            throw new FailLoginException(ErrorCode.FAIL_TO_LOGIN);
        }

        return LoginOutDto.of(
                findUser.getId(),
                findUser.getAccountName(),
                jwtService.createAccessToken(findUser.getId(), findUser.getAccountName()),
                jwtService.createRefreshToken(findUser.getId(), findUser.getAccountName())
        );

    }

}
