package dev.yogizogi.domain.auth.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Schema(name = "accessToken 재발급 응답 Dto")
@Getter
@Builder(access = AccessLevel.PRIVATE)
public class ReissueAccessTokenOutDto {

    @Schema(description = "유저 식별자")
    private UUID id;

    @Schema(description = "어세스 토큰")
    private String accessToken;

    public static ReissueAccessTokenOutDto of(UUID id, String accessToken) {
        return ReissueAccessTokenOutDto.builder()
                .id(id)
                .accessToken(accessToken)
                .build();
    }

}
