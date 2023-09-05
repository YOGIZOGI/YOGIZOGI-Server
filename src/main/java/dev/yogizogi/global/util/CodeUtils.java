package dev.yogizogi.global.util;

import static dev.yogizogi.global.common.constant.Number.VERIFICATION_CODE_LENGTH;

import java.util.Random;

public class CodeUtils {

    public static String verification() {

        Random random = new Random();
        StringBuilder code = new StringBuilder();

        for (int i = 0 ; i < VERIFICATION_CODE_LENGTH ; i++) {
            code.append(random.nextInt(9));
        }

        return code.toString();

    }

}
