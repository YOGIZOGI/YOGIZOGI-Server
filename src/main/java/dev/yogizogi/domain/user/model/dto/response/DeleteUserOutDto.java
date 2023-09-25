package dev.yogizogi.domain.user.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Schema(name = "회원탈퇴 응답 Dto")
@Getter
@Builder(access = AccessLevel.PRIVATE)
public class DeleteUserOutDto {

    @Schema(description = "아이디", example = "yogizogi")
    private String accountName;

    public static DeleteUserOutDto of(String accountName) {
        return DeleteUserOutDto.builder()
                .accountName(accountName)
                .build();
    }

}
