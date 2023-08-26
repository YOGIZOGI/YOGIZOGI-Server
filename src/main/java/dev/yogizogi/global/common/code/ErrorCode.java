package dev.yogizogi.global.common.code;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum ErrorCode {

    FAIL_TEST(HttpStatus.BAD_REQUEST.value(), "요청 실패");

    private final int code;
    private final String message;

}
