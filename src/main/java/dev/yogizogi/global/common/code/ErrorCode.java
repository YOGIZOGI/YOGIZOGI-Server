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
    DUPLICATE_MEMBER_INFORMATION(HttpStatus.BAD_REQUEST.value(), "중복되는 멤버 정보(닉네임, 아이디, 핸드폰 번호)가 존재합니다.");

    private final int code;
    private final String message;

}
