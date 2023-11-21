package dev.yogizogi.domain.meokmap.exception;

import dev.yogizogi.global.common.code.ErrorCode;
import dev.yogizogi.global.common.exception.BaseException;
import lombok.Getter;

@Getter
public class NotExistMeokMapException extends BaseException {

    public NotExistMeokMapException(ErrorCode errorCode) {
        super(errorCode);
    }

}
