package dev.yogizogi.domain.security.exception;

import dev.yogizogi.global.common.code.ErrorCode;
import dev.yogizogi.global.common.exception.BaseException;
import lombok.Getter;

@Getter
public class ExpiredTokenException extends BaseException {

    public ExpiredTokenException(ErrorCode errorCode) {
        super(errorCode);
    }

}
