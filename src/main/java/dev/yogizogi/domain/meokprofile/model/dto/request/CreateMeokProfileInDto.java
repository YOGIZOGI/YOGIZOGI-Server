package dev.yogizogi.domain.meokprofile.model.dto.request;

import dev.yogizogi.domain.meokprofile.model.entity.Intensity;
import dev.yogizogi.domain.meokprofile.model.entity.MeokProfile;
import dev.yogizogi.domain.meokprofile.model.entity.Preference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


@Getter
@AllArgsConstructor
@Schema(name = "먹프로필 등록 요청 DTO")
public class CreateMeokProfileInDto {

    @Min(value = 1) @Max(value = 5)
    @Schema(example = "4")
    private Long spicyPreference;

    @Min(value = 1) @Max(value = 5)
    @Schema(example = "3")
    private Long spicyIntensity;

    @Min(value = 1) @Max(value = 5)
    @Schema(example = "3")
    private Long saltyPreference;

    @Min(value = 1) @Max(value = 5)
    @Schema(example = "2")
    private Long saltyIntensity;

    @Min(value = 1) @Max(value = 5)
    @Schema(example = "2")
    private Long sweetnessPreference;

    @Min(value = 1) @Max(value = 5)
    @Schema(example = "3")
    private Long sweetnessIntensity;

    @Builder
    public static MeokProfile toEntity(Preference preference, Intensity intensity) {

        return MeokProfile.builder()
                .preference(preference)
                .intensity(intensity)
                .build();

    }

}
