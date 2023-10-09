package dev.yogizogi.global.common.advice;

import static dev.yogizogi.global.common.model.constant.Format.VALIDATION_RESULT;

import dev.yogizogi.global.common.code.ErrorCode;
import dev.yogizogi.global.common.exception.BaseException;
import dev.yogizogi.global.common.model.response.Failure;
import dev.yogizogi.global.common.status.MessageStatus;
import dev.yogizogi.global.util.ResponseUtils;
import java.util.ArrayList;
import lombok.extern.slf4j.Slf4j;
import net.nurigo.sdk.message.exception.NurigoApiKeyException;
import net.nurigo.sdk.message.exception.NurigoBadRequestException;
import net.nurigo.sdk.message.exception.NurigoUnknownException;
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

    @ExceptionHandler({
            NurigoUnknownException.class,
            NurigoBadRequestException.class,
            NurigoApiKeyException.class
    })
    protected ResponseEntity handleCoolSmsException(NurigoUnknownException exception) {

        StringBuilder message = new StringBuilder();
        message.append(MessageStatus.FAIL).append(" = {").append(exception.getMessage()).append("}");

        return ResponseUtils.error(
                Failure.builder()
                        .message(message)
                        .build(),
                ErrorCode.FAIL_TO_SEND_MESSAGE
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
                ErrorCode.FAIL_TO_VALIDATE
        );

    }

}
