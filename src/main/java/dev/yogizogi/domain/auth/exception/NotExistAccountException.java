package dev.yogizogi.domain.auth.exception;

import dev.yogizogi.global.common.code.ErrorCode;
import dev.yogizogi.global.common.exception.BaseException;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
@Getter
public class NotExistAccountException extends BaseException {

    public NotExistAccountException(ErrorCode errorCode) {
        super(errorCode);
    }

}
