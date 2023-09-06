package dev.yogizogi.global.common.code;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum ErrorCode {

    FAIL_TEST(HttpStatus.BAD_REQUEST.value(), "요청 실패"),

    WRONG_RESPONSE(HttpStatus.BAD_REQUEST.value(), "잘못된 요청입니다."),
    DUPLICATE_ACCOUNT_NAME(HttpStatus.BAD_REQUEST.value(), "중복된 아이디입니다."),
    DUPLICATE_NICKNAME(HttpStatus.BAD_REQUEST.value(), "중복된 닉네임입니다."),
    DUPLICATE_PHONE_NUMBER(HttpStatus.BAD_REQUEST.value(), "중복된 핸드폰 번호입니다.")
    ;

    private final int code;
    private final String message;

}
