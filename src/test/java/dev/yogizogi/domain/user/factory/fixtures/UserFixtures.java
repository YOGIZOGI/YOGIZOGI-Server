package dev.yogizogi.domain.user.factory.fixtures;

import dev.yogizogi.domain.user.model.entity.FirstLoginStatus;
import java.util.UUID;

public class UserFixtures {

    public static UUID 식별자 = UUID.fromString("11ee5886-0ec1-126a-83a9-83536d67b154");
    public static String 핸드폰_번호 = "01012345678";
    public static FirstLoginStatus 처음_로그인 = FirstLoginStatus.ACTIVE;
    public static String 닉네임 = "test";
    public static String 역할 = "ROLE_USER";

}
