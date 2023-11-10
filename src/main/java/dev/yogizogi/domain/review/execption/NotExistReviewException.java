package dev.yogizogi.domain.review.execption;

import dev.yogizogi.global.common.code.ErrorCode;
import dev.yogizogi.global.common.exception.BaseException;
import lombok.Getter;

@Getter
public class NotExistReviewException extends BaseException {

    public NotExistReviewException(ErrorCode errorCode) {
        super(errorCode);
    }
}
