package dev.yogizogi.global.util;

import dev.yogizogi.global.common.model.response.ApiResponse;
import dev.yogizogi.global.common.model.response.Result;
import dev.yogizogi.global.common.status.response.ResponseStatus;

public class ResponseUtils {

    public static ApiResponse ok(Result result) {
        return ApiResponse.builder()
                .status(ResponseStatus.SUCCESS)
                .result(result)
                .build();
    }

    public static ApiResponse error(Result result) {
        return ApiResponse.builder()
                .status(ResponseStatus.FAILURE)
                .result(result)
                .build();
    }

}
