package dev.yogizogi.global.common.model.response;

import dev.yogizogi.global.common.code.SuccessCode;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Success<T> implements Result {

    private int code;
    private String message;
    private T data;

    @Builder
    private Success(SuccessCode code, T data) {

        this.code = code.getCode();
        this.message = code.getMessage();
        this.data = data;

    }


}
