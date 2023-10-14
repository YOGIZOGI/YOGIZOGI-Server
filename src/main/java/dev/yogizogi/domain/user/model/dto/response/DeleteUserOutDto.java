package dev.yogizogi.domain.user.model.dto.response;

import dev.yogizogi.global.common.status.BaseStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@Schema(name = "회원탈퇴 응답 Dto")
public class DeleteUserOutDto {

    @Schema(description = "아이디", example = "01012345678")
    private String phoneNumber;

    @Schema(description = "상태", example = "INACTIVE", allowableValues = {"ACTIVE", "INACTIVE"})
    private BaseStatus status;

    public static DeleteUserOutDto of(String accountName, BaseStatus status) {
        return DeleteUserOutDto.builder()
                .phoneNumber(accountName)
                .status(status)
                .build();
    }

}
