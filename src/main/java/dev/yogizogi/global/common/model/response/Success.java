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
public class Success<T>  implements Result {

    private String timestamp;
    private String trackingId;
    private ResponseStatus status;
    private T data;

    @Builder
    public Success(T data) {

        this.timestamp = LocalDateTime.now().toString();
        this.trackingId = UUID.randomUUID().toString();
        this.status = ResponseStatus.SUCCESS;
        this.data = data;

    }

}
