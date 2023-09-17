package dev.yogizogi.global.common.model.constant;

import java.util.ArrayList;
import java.util.List;

public class Number {

    public static final int VERIFICATION_CODE_LENGTH = 6;
    public static final long VERIFICATION_CODE_EXPIRATION_TIME = 60 * 5L;
    public static final List<String> COOLSMS_SUCCESS_CODE = new ArrayList<>(List.of("2000", "3000"));

}
