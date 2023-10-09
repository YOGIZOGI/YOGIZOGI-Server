package dev.yogizogi.domain.security.factory.fixtures;

import java.util.UUID;

public class TokenFixtures {

    public final static UUID 토큰에_포함할_식별자 = UUID.fromString("11ee5886-0ec1-126a-83a9-83536d67b154");
    public final static String 토큰에_포함할_핸드폰_번호 = "01012345678";

    public final static UUID 토큰에_포함된_식별자 = UUID.fromString("11ee5886-0ec1-126a-83a9-83536d67b154");
    public final static String 토큰에_포함된_핸드포_번호 = "01012345678";

    public final static String 토큰 = "TOKEN";
    public final static String 어세스_토큰 = "ACCESS";
    public final static String 발행한_어세스_토큰 = "ACCESS";

    public final static String 리프레쉬_토큰 = "REFRESH";
    public final static String 저장된_리프레쉬_토큰 = "REFRESH";

}
