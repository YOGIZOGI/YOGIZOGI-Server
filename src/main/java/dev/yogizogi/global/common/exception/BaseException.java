package dev.yogizogi.global.common.exception;

import dev.yogizogi.global.common.code.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BaseException extends RuntimeException {

    private ErrorCode errorCode;

}
