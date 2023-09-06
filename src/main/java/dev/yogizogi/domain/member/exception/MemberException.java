package dev.yogizogi.domain.member.exception;

import dev.yogizogi.global.common.code.ErrorCode;
import dev.yogizogi.global.common.exception.BaseException;
import lombok.Getter;

@Getter
public class MemberException extends BaseException {

    public MemberException(ErrorCode error) {
        super(error);
    }

}
