package dev.yogizogi.domain.authorization.factory.fixtures;

import dev.yogizogi.domain.user.model.entity.FirstLoginStatus;
import java.util.UUID;

public class LoginFixtures {

    public static String 받은_핸드폰_번호 = "01012345678";
    public static String 받은_비밀번호 = "yogi0302!";
    public static UUID 로그인_한_유저 = UUID.fromString("11ee5886-0ec1-126a-83a9-83536d67b154");
    public static FirstLoginStatus 처음_로그인 = FirstLoginStatus.ACTIVE;
    public final static String 발행한_어세스_토큰 = "ACCESS";
    public final static String 발행한_리프레쉬_토큰 = "REFRESH";

}
