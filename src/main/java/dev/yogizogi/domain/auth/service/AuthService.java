package dev.yogizogi.domain.auth.service;

import dev.yogizogi.domain.auth.model.dto.response.VerifyCodeOutDto;
import dev.yogizogi.domain.member.exception.MemberException;
import dev.yogizogi.domain.member.repository.MemberRepository;
import dev.yogizogi.global.common.code.ErrorCode;
import dev.yogizogi.global.common.status.VerificationStatus;
import dev.yogizogi.global.util.CodeUtils;
import dev.yogizogi.global.util.RedisUtils;
import dev.yogizogi.global.util.SmsUtils;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final SmsUtils smsUtils;
    private final RedisUtils redisUtils;

    public SingleMessageSentResponse sendVerificationCode(String phoneNumber) {

        if (!memberRepository.findByPhoneNumber(phoneNumber).isEmpty()) {
            throw new MemberException(ErrorCode.DUPLICATE_PHONE_NUMBER);
        }

        return smsUtils.sendOne(phoneNumber, CodeUtils.verification());

    }


    public VerifyCodeOutDto checkVerificationCode(String phoneNumber, String code) {

        VerificationStatus status = VerificationStatus.PASS;
        String findCode = redisUtils.findByKey(phoneNumber);

        if (!Objects.equals(findCode, code)) {
            status = VerificationStatus.NOT_PASS;
        }

        return VerifyCodeOutDto.of(status, phoneNumber);

    }

}
