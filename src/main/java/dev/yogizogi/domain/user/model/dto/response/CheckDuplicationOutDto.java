package dev.yogizogi.domain.user.model.dto.response;

import dev.yogizogi.global.common.status.DuplicationStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Schema(name = "중복확인 응답 Dto")
@Getter
@Builder(access = AccessLevel.PRIVATE)
public class CheckDuplicationOutDto {


    @Schema(description = "결과", allowableValues = {"EXIST", "NOT_EXIST"})
    private DuplicationStatus status;

    @Schema(description = "확인한 데이터")
    private String checked;

    public static CheckDuplicationOutDto  of(DuplicationStatus status, String data) {
        return CheckDuplicationOutDto.builder()
                .status(status)
                .checked(data)
                .build();
    }

}
