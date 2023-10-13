package dev.yogizogi.domain.meokprofile.model.dto.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@Schema(name = "먹프로필 등록 응답 Dto")
public class CreateMeokProfileOutDto {

    @Schema(description = "생성된 프로필 식별자")
    private Long id;

    public static CreateMeokProfileOutDto of(Long id) {

        return CreateMeokProfileOutDto.builder()
                .id(id)
                .build();

    }

}
