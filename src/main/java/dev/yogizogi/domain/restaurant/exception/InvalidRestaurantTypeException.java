package dev.yogizogi.domain.restaurant.exception;

import dev.yogizogi.global.common.code.ErrorCode;
import dev.yogizogi.global.common.exception.BaseException;
import lombok.Getter;

@Getter
public class InvalidRestaurantTypeException extends BaseException {

    public InvalidRestaurantTypeException(ErrorCode errorCode) {
        super(errorCode);
    }

}
