package dev.yogizogi.domain.meokmap.exception;

import dev.yogizogi.global.common.code.ErrorCode;
import dev.yogizogi.global.common.exception.BaseException;
import lombok.Getter;

@Getter
public class NotExistRestaurantOnMeokMapException extends BaseException {

    public NotExistRestaurantOnMeokMapException(ErrorCode errorCode) {
        super(errorCode);
    }

}
