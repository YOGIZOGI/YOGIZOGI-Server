package dev.yogizogi.domain.authorization.service;

import static dev.yogizogi.global.common.model.constant.Number.COOLSMS_SUCCESS_CODE;

import dev.yogizogi.domain.authorization.model.dto.response.SendVerificationCodeOutDto;
import dev.yogizogi.domain.authorization.model.dto.response.VerifyCodeOutDto;
import dev.yogizogi.domain.user.exception.AlreadyUsePhoneNumberException;
import dev.yogizogi.domain.user.service.UserService;
import dev.yogizogi.global.common.code.ErrorCode;
import dev.yogizogi.global.common.status.MessageStatus;
import dev.yogizogi.global.common.status.VerificationStatus;
import dev.yogizogi.global.util.RedisUtils;
import dev.yogizogi.infra.coolsms.CoolSmsService;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class VerificationService {

    private final UserService userService;

    private final CoolSmsService coolSmsService;
    private final RedisUtils redisUtils;

    @Transactional(readOnly = true)
    public SendVerificationCodeOutDto sendVerificationCodeForSignUp(String phoneNumber) {

        if (userService.isUsedPhoneNumber(phoneNumber)) {
            throw new AlreadyUsePhoneNumberException(ErrorCode.ALREADY_USE_PHONE_NUMBER);
        }

        SingleMessageSentResponse result = coolSmsService.sendOne(phoneNumber);
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


}
