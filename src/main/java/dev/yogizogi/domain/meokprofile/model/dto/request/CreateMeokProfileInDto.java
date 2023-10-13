package dev.yogizogi.domain.meokprofile.model.dto.request;

import dev.yogizogi.domain.meokprofile.model.entity.Intensity;
import dev.yogizogi.domain.meokprofile.model.entity.MeokProfile;
import dev.yogizogi.domain.meokprofile.model.entity.Preference;
import dev.yogizogi.domain.user.model.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


@Getter
@AllArgsConstructor
@Schema(name = "먹프로필 등록 요청 Dto")
public class CreateMeokProfileInDto {

    @Min(value = 1)
    @Max(value = 5)
    @Schema(description = "매운맛 선호도", example = "4")
    private Long spicyPreference;

    @Min(value = 1)
    @Max(value = 5)
    @Schema(description = "매운맛 강도", example = "3")
    private Long spicyIntensity;

    @Min(value = 1)
    @Max(value = 5)
    @Schema(description = "짠맛 선호도", example = "2")
    private Long saltyPreference;

    @Min(value = 1)
    @Max(value = 5)
    @Schema(description = "짠맛 강도", example = "2")
    private Long saltyIntensity;

    @Min(value = 1)
    @Max(value = 5)
    @Schema(description = "단맛 선호도", example = "3")
    private Long sweetnessPreference;

    @Min(value = 1)
    @Max(value = 5)
    @Schema(description = "단맛 강도", example = "3")
    private Long sweetnessIntensity;

    @Builder
    public static MeokProfile toEntity(
            User user,
            long spicyPreference, long spicyIntensity,
            long saltyPreference, long saltyIntensity,
            long sweetnessPreference, long sweetnessIntensity
    ) {

        return MeokProfile.builder()
                .user(user)
                .preference(Preference.builder()
                        .spicyPreference(spicyPreference)
                        .saltyPreference(saltyPreference)
                        .sweetnessPreference(sweetnessPreference)
                        .build())
                .intensity(Intensity.builder()
                        .spicyIntensity(spicyIntensity)
                        .saltyIntensity(saltyIntensity)
                        .sweetnessIntensity(sweetnessIntensity)
                        .build())
                .build();

    }

}
