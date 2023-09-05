package dev.yogizogi.domain.auth.service;

import dev.yogizogi.global.util.CodeUtils;
import dev.yogizogi.global.util.SmsUtils;
import lombok.RequiredArgsConstructor;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class AuthService {

    private final SmsUtils smsUtils;

    public SingleMessageSentResponse sendVerificationCode(String phoneNumber) {

       return smsUtils.sendOne(phoneNumber, CodeUtils.verification());

    }


}
