package dev.yogizogi.global.common.model.response;

import dev.yogizogi.global.common.status.response.ResponseStatus;
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
    private ResponseStatus status;
    private T message;

    @Builder
    public Failure(ResponseStatus status, T message) {

        this.timestamp = LocalDateTime.now().toString();
        this.trackingId = UUID.randomUUID().toString();
        this.status = ResponseStatus.FAILURE;
        this.message = message;

    }

}
