package dev.yogizogi.domain.auth.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Schema(name = "로그인 응답 Dto")
@Getter
@Builder(access = AccessLevel.PRIVATE)
public class LoginOutDto {

    @Schema(name = "유저 식별자")
    private UUID id;

    public static LoginOutDto of(UUID id) {
        return LoginOutDto.builder()
                .id(id)
                .build();
    }

}
