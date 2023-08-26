package dev.yogizogi.global.common.model.response;

import dev.yogizogi.global.common.status.response.ResponseStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ApiResponse {

    private ResponseStatus status;
    private Result result;

    @Builder
    private ApiResponse(ResponseStatus status, Result result) {
        this.status = status;
        this.result = result;
    }

}
