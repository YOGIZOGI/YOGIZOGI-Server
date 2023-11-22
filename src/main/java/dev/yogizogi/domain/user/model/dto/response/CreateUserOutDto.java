package dev.yogizogi.domain.user.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@Schema(name = "회원가입 응답 DTO")
public class CreateUserOutDto {

    @Schema(description = "식별자")
    private UUID id;

    @Schema(description = "아이디", example = "0101234567")
    private String phoneNumber;

    public static CreateUserOutDto of(UUID id, String phoneNumber) {
        return CreateUserOutDto.builder()
                .id(id)
                .phoneNumber(phoneNumber)
                .build();
    }

}
