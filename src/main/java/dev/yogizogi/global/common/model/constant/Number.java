package dev.yogizogi.global.common.model.constant;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class Number {

    public static final long VERIFICATION_CODE_LENGTH = 6;
    public static final long VERIFICATION_CODE_EXPIRATION_TIME = Duration.ofMinutes(5).toMillis();
    public static final long PRE_SIGNED_URL_EXPIRATION_TIME = Duration.ofMinutes(10).toMillis();
    public static final List<String> COOLSMS_SUCCESS_CODE = new ArrayList<>(List.of("2000", "3000"));

}
