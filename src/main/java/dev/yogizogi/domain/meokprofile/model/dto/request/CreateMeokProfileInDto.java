package dev.yogizogi.domain.meokprofile.model.dto.request;

import dev.yogizogi.domain.meokprofile.model.entity.MeokProfile;
import dev.yogizogi.domain.meokprofile.model.entity.Preference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "먹프로필 등록 요청 DTO")
public class CreateMeokProfileInDto {

    @Min(value = 1) @Max(value = 5)
    @Schema(example = "4")
    private Integer spicyPreference;

    @Min(value = 1) @Max(value = 5)
    @Schema(example = "3")
    private Integer saltyPreference;

    @Min(value = 1) @Max(value = 5)
    @Schema(example = "2")
    private Integer sweetnessPreference;

    @Builder
    public static MeokProfile toEntity(Preference preference) {

        return MeokProfile.builder()
                .preference(preference)
                .build();

    }

}
