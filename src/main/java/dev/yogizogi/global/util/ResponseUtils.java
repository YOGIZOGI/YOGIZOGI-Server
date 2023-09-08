package dev.yogizogi.global.util;

import dev.yogizogi.global.common.code.ErrorCode;
import dev.yogizogi.global.common.model.response.Result;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseUtils {

    public static ResponseEntity ok(Result result) {
        return new ResponseEntity(
                result,
                HttpStatus.OK
        );
    }

    public static ResponseEntity created(Result result) {
        return new ResponseEntity(
                result,
                HttpStatus.CREATED
        );
    }

    public static ResponseEntity error(Result result, ErrorCode code) {
        return new ResponseEntity(
                result,
                code.getCode()
        );
    }

}
