package dev.yogizogi.domain.menu.exception;

import dev.yogizogi.global.common.code.ErrorCode;
import dev.yogizogi.global.common.exception.BaseException;
import lombok.Getter;

@Getter
public class NotExistMenuException extends BaseException {

    public NotExistMenuException(ErrorCode errorCode) {
        super(errorCode);
    }
}
