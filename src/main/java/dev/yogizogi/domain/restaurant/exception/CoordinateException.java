package dev.yogizogi.domain.restaurant.exception;

import dev.yogizogi.global.common.code.ErrorCode;
import dev.yogizogi.global.common.exception.BaseException;
import lombok.Getter;

@Getter
public class CoordinateException extends BaseException {

    public CoordinateException(ErrorCode errorCode) {
        super(errorCode);
    }

}
