package dev.yogizogi.domain.review.exception;

import dev.yogizogi.global.common.code.ErrorCode;
import dev.yogizogi.global.common.exception.BaseException;
import lombok.Getter;

@Getter
public class UnauthorizedRestaurantException extends BaseException {

    public UnauthorizedRestaurantException(ErrorCode errorCode) {
        super(errorCode);
    }

}
