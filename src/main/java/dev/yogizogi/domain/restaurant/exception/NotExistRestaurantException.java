package dev.yogizogi.domain.restaurant.exception;

import dev.yogizogi.global.common.code.ErrorCode;
import dev.yogizogi.global.common.exception.BaseException;
import lombok.Getter;

@Getter
public class NotExistRestaurantException extends BaseException {

    public NotExistRestaurantException(ErrorCode errorCode) {
        super(errorCode);
    }

}
