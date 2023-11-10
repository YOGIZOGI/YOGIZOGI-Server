package dev.yogizogi.global.common.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import dev.yogizogi.global.common.status.response.ResponseStatus;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@JsonInclude(Include.NON_EMPTY)
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
        this.status = getStatus(data);
        this.data = data;

    }

    private ResponseStatus getStatus(T data) {
        if (data == null) {
            return ResponseStatus.NO_CONTENT;
        }

        return ResponseStatus.SUCCESS;
    }

}
