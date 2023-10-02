package dev.yogizogi.domain.authorization.exception;

import dev.yogizogi.global.common.code.ErrorCode;
import dev.yogizogi.global.common.exception.BaseException;
import lombok.Getter;

@Getter
public class AuthException extends BaseException {

    public AuthException(ErrorCode error) {
        super(error);
    }

}
