package dev.yogizogi.domain.meokprofile.model.dto.response;

import dev.yogizogi.domain.meokprofile.model.entity.Preference;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@Schema(name = "먹프로필 등록 응답 DTO")
public class CreateMeokProfileOutDto {

    @Schema(description = "선호도")
    private Preference preference;

    public static CreateMeokProfileOutDto of(Preference preference) {

        return CreateMeokProfileOutDto.builder()
                .preference(preference)
                .build();

    }

}
