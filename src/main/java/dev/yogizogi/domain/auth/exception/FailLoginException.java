package dev.yogizogi.domain.auth.exception;

import dev.yogizogi.global.common.code.ErrorCode;
import dev.yogizogi.global.common.exception.BaseException;
import lombok.Getter;

@Getter
public class FailLoginException extends BaseException {

    public FailLoginException(ErrorCode errorCode) {
        super(errorCode);
    }

}
