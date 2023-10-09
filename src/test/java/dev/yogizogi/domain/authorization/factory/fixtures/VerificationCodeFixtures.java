package dev.yogizogi.domain.authorization.factory.fixtures;

import dev.yogizogi.global.util.UuidUtils;
import net.nurigo.sdk.message.model.MessageType;

public class VerificationCodeFixtures {

    public static String 받을_핸드폰_번호 = "01012345678";
    public static String 받은_핸드폰_번호 = "01012345678";

    public static String 받은_인증코드 = "123456";
    public static String 저장된_인증코드 = "123456";
    public static String 인증코드 = "123456";

    public static String 그룹_아이디 = String.valueOf(UuidUtils.createSequentialUUID());
    public static String 발신자 = "01012345678";
    public static String 수신자 = "01087654321";
    public static MessageType 메시지_종류 = MessageType.SMS;
    public static String 상태_메시지 =  "상태_메시지";
    public static String 나라 =  "대한민국";
    public static String 메시지_아이디 =  String.valueOf(UuidUtils.createSequentialUUID());
    public static String 성공_상태_코드 = "2000";
    public static String 실패_상태_코드 = "2001";
    public static String 계좌_아이디 =  String.valueOf(UuidUtils.createSequentialUUID());

}
