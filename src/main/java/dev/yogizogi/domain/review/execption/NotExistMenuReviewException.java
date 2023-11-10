package dev.yogizogi.domain.review.execption;

import dev.yogizogi.global.common.code.ErrorCode;
import dev.yogizogi.global.common.exception.BaseException;
import lombok.Getter;

@Getter
public class NotExistMenuReviewException extends BaseException {

    public NotExistMenuReviewException(ErrorCode errorCode) {
        super(errorCode);
    }
}
