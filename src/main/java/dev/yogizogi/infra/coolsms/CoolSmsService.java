package dev.yogizogi.infra.coolsms;

import static dev.yogizogi.global.common.model.constant.Format.VERIFICATION_CODE_MESSAGE;
import static dev.yogizogi.global.common.model.constant.Number.VERIFICATION_CODE_EXPIRATION_TIME;

import dev.yogizogi.global.util.RedisUtils;
import lombok.RequiredArgsConstructor;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CoolSmsService {

    private final DefaultMessageService messageService;
    private final RedisUtils redisUtils;
    private final Message message;

    public SingleMessageSentResponse sendOne(String to, String code) {

        setMessage(to, code);
        SingleMessageSentResponse response = messageService.sendOne(new SingleMessageSendingRequest(message));
        redisUtils.saveWithExpirationTime(to, code, VERIFICATION_CODE_EXPIRATION_TIME);

        return response;

    }

    private void setMessage(String to, String code) {
        message.setTo(to);
        message.setText(String.format(VERIFICATION_CODE_MESSAGE, code));
    }

}
