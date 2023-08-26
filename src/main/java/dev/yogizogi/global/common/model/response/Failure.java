package dev.yogizogi.global.common.model.response;

import dev.yogizogi.global.common.code.ErrorCode;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Failure<T> implements Result {

    private String timestamp;
    private String trackingId;
    private int code;
    private String message;
    private T data;

    @Builder
    public Failure(ErrorCode code, T data) {

        this.timestamp = LocalDateTime.now().toString();
        this.trackingId = UUID.randomUUID().toString();
        this.code = code.getCode();
        this.message = code.getMessage();
        this.data = data;

    }

}
