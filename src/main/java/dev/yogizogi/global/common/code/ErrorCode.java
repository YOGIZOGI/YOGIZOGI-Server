package dev.yogizogi.global.common.code;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum ErrorCode {

    /**
     * Code : 400
     * Bad Request
     */
    FAIL_TEST(HttpStatus.BAD_REQUEST, "요청 실패"),
    WRONG_RESPONSE(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
    DUPLICATE_ACCOUNT_NAME(HttpStatus.BAD_REQUEST, "중복된 아이디입니다."),
    DUPLICATE_NICKNAME(HttpStatus.BAD_REQUEST, "중복된 닉네임입니다."),
    DUPLICATE_PHONE_NUMBER(HttpStatus.BAD_REQUEST, "중복된 핸드폰 번호입니다."),


    /**
     * Code : 401
     * - Unauthorized (권한이 없어 인증 불가)
     */

    /**
     * Code : 403
     * - Forbidden (요청한 자원에 대해 권한 없음)
     */
    FAIL_LOGIN(HttpStatus.FORBIDDEN, "아이디 또는 비밀번호를 잘못 입력했습니다."),


    /**
     * Code : 404
     * - Not Found (존재하지 않는 자원임)
     */
    NOT_EXIST_ACCOUNT(HttpStatus.NOT_FOUND, "존재하지 않는 계정입니다.");


    private final HttpStatus code;
    private final String message;

}
