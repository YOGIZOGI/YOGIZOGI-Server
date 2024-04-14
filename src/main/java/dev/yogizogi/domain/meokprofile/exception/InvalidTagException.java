package dev.yogizogi.domain.meokprofile.exception;

import dev.yogizogi.global.common.code.ErrorCode;
import dev.yogizogi.global.common.exception.BaseException;
import lombok.Getter;

@Getter
public class InvalidTagException extends BaseException {

    public InvalidTagException(ErrorCode errorCode) {
        super(errorCode);
    }

}
