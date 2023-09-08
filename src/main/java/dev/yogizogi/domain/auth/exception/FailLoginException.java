package dev.yogizogi.domain.auth.exception;

import dev.yogizogi.global.common.code.ErrorCode;
import dev.yogizogi.global.common.exception.BaseException;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
@Getter
public class FailLoginException extends BaseException {

    public FailLoginException(ErrorCode errorCode) {
        super(errorCode);
    }

}
