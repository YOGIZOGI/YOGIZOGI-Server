package dev.yogizogi.domain.authorization.factory.dto;

import static dev.yogizogi.domain.authorization.factory.fixtures.VerificationCodeFixtures.*;
import static dev.yogizogi.domain.user.factory.fixtures.UserFixtures.핸드폰번호;

import dev.yogizogi.domain.authorization.model.dto.response.SendVerificationCodeOutDto;
import dev.yogizogi.domain.authorization.model.dto.response.VerifyCodeOutDto;
import dev.yogizogi.global.common.status.MessageStatus;
import dev.yogizogi.global.common.status.VerificationStatus;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;

public class VerificationFactory {

    public static SingleMessageSentResponse successSingleMessageSentResponse() {
        return new SingleMessageSentResponse(
                그룹_아이디,
                발신자,
                수신자,
                메시지_종류,
                상태_메시지,
                나라,
                메시지_아이디,
                성공_상태_코드,
                계좌_아이디
        );
    }

    public static SingleMessageSentResponse failSingleMessageSentResponse() {
        return new SingleMessageSentResponse(
                그룹_아이디,
                발신자,
                수신자,
                메시지_종류,
                상태_메시지,
                나라,
                메시지_아이디,
                실패_상태_코드,
                계좌_아이디
        );
    }

    public static SendVerificationCodeOutDto sendVerificationCodeOutDto() {
        return SendVerificationCodeOutDto.of(MessageStatus.SUCCESS, 상태_메시지);
    }

    public static VerifyCodeOutDto pass() {
        return VerifyCodeOutDto.of(VerificationStatus.PASS, 핸드폰번호);
    }

    public static VerifyCodeOutDto notPass() {
        return VerifyCodeOutDto.of(VerificationStatus.NOT_PASS, 핸드폰번호);
    }

}
