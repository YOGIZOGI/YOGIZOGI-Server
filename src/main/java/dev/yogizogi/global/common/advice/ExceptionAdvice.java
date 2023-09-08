package dev.yogizogi.global.common.advice;

import static dev.yogizogi.global.common.model.constant.Format.VALIDATION_RESULT;

import dev.yogizogi.domain.auth.exception.FailLoginException;
import dev.yogizogi.global.common.code.ErrorCode;
import dev.yogizogi.global.common.exception.BaseException;
import dev.yogizogi.global.common.model.response.Failure;
import dev.yogizogi.global.util.ResponseUtils;
import java.util.ArrayList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler({BaseException.class})
    protected ResponseEntity handleBaseException(BaseException exception) {

        Failure result = Failure.builder()
                .message(exception.getErrorCode().getMessage())
                .build();

        log.error("Error occurred in controller advice : [id={}]", result.getTrackingId());

        return ResponseUtils.error(
                result,
                exception.getErrorCode()
        );

    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    protected ResponseEntity handleValidException(MethodArgumentNotValidException exception) {

        BindingResult bindingResult = exception.getBindingResult();
        ArrayList<String> errors = new ArrayList<>();

        for (FieldError fieldError : bindingResult.getFieldErrors()) {

            errors.add
                    (String
                            .format(
                                    VALIDATION_RESULT,
                                    fieldError.getDefaultMessage(),
                                    fieldError.getField(),
                                    fieldError.getRejectedValue()
                            )
                    );
        }

        return ResponseUtils.error(
                Failure.builder()
                        .message(errors)
                        .build(),
                ErrorCode.FAIL_VALIDATION
        );

    }

}
