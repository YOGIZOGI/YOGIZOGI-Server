package dev.yogizogi.global.common.advice;

import dev.yogizogi.global.common.exception.BaseException;
import dev.yogizogi.global.common.model.response.ApiResponse;
import dev.yogizogi.global.common.model.response.Failure;
import dev.yogizogi.global.util.response.ResponseUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler({BaseException.class})
    protected ApiResponse handleBaseException(BaseException exception) {

        Failure result = Failure.builder().code(exception.getErrorCode()).data(null).build();
        log.error("Error occurred in controller advice : [id={}]", result.getTrackingId());

        return ResponseUtils.error(result);

    }

    

}
