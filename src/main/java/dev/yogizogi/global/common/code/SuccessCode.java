package dev.yogizogi.global.common.code;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum SuccessCode {

    SUCCESS_TEST(HttpStatus.OK, "테스트 성공"),

    OK(HttpStatus.OK, "요청 완료"),
    CREATED(HttpStatus.CREATED, "생성 완료");

    private final HttpStatus code;
    private final String message;

}
