package dev.yogizogi.domain.map.exception;

import dev.yogizogi.global.common.code.ErrorCode;
import dev.yogizogi.global.common.exception.BaseException;
import lombok.Getter;

@Getter
public class FailToRemoveRestaurantOnMeokMapException extends BaseException {

    public FailToRemoveRestaurantOnMeokMapException(ErrorCode errorCode) {
        super(errorCode);
    }

}
