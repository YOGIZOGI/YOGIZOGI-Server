package dev.yogizogi.global.common.code;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum SuccessCode {

    SUCCESS_TEST(HttpStatus.OK.value(), "테스트 성공");

    private final int code;
    private final String message;

}
