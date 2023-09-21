package dev.yogizogi.domain.member.model.dto.response;

import dev.yogizogi.domain.member.model.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Schema(name = "회원가입 응답 Dto")
@Getter
@Builder(access = AccessLevel.PRIVATE)
public class CreateUserOutDto {

    @Schema(description = "식별자")
    private UUID id;

    @Schema(description = "아이디", example = "yogizogi")
    private String accountName;

    public static CreateUserOutDto of(User user) {
        return CreateUserOutDto.builder()
                .id(user.getId())
                .accountName(user.getAccountName())
                .build();
    }

}
