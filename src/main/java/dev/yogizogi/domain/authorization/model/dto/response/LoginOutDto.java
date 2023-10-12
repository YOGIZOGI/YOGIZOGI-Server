package dev.yogizogi.domain.authorization.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Schema(name = "로그인 응답 Dto")
@Getter
@Builder(access = AccessLevel.PRIVATE)
public class LoginOutDto {

    @Schema(description = "유저 식별자")
    private UUID id;

    @Schema(description = "최초 로그인 여부")
    private boolean isFirstLogin;

    @Schema(description = "어세스 토큰")
    private String accessToken;

    @Schema(description = "리프레쉬 토큰")
    private String refreshToken;

    public static LoginOutDto of(UUID id, boolean isFirstLogin, String accessToken, String refreshToken) {
        return LoginOutDto.builder()
                .id(id)
                .isFirstLogin(isFirstLogin)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

}
