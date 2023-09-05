package dev.yogizogi.global.util;

import static dev.yogizogi.global.common.constant.Format.VERIFICATION_CODE_MESSAGE;

import lombok.RequiredArgsConstructor;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SmsUtils {

    private final DefaultMessageService messageService;
    private final Message message;

    public SingleMessageSentResponse sendOne(String to, String code) {

        message.setTo(to);
        message.setText(VERIFICATION_CODE_MESSAGE + code);
        return  messageService.sendOne(new SingleMessageSendingRequest(message));

    }

}
