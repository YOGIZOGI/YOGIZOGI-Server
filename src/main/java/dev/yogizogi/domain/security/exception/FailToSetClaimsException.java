package dev.yogizogi.domain.security.exception;

import dev.yogizogi.global.common.code.ErrorCode;
import dev.yogizogi.global.common.exception.BaseException;
import lombok.Getter;

@Getter
public class FailToSetClaimsException extends BaseException {

    public FailToSetClaimsException(ErrorCode errorCode) {
        super(errorCode);
    }

}
