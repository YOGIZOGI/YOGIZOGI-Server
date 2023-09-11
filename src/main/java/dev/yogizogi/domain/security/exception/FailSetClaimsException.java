package dev.yogizogi.domain.security.exception;

import dev.yogizogi.global.common.code.ErrorCode;
import dev.yogizogi.global.common.exception.BaseException;
import lombok.Getter;

@Getter
public class FailSetClaimsException extends BaseException {

    public FailSetClaimsException(ErrorCode errorCode) {
        super(errorCode);
    }

}
