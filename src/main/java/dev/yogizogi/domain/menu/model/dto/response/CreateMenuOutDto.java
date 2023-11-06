package dev.yogizogi.domain.menu.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@Schema(name = "메뉴 등록 응답 Dto")
public class CreateMenuOutDto {

    @Schema(description = "등록된 메뉴 이름")
    private String name;

    public static CreateMenuOutDto of(String name) {
        return CreateMenuOutDto.builder()
                .name(name)
                .build();
    }

}
