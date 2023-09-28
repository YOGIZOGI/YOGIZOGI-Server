package dev.yogizogi.domain.user.exception;

import dev.yogizogi.global.common.code.ErrorCode;
import dev.yogizogi.global.common.exception.BaseException;
import lombok.Getter;

@Getter
public class DuplicatedPhoneNumberException extends BaseException {

    public DuplicatedPhoneNumberException(ErrorCode error) {
        super(error);
    }

}
