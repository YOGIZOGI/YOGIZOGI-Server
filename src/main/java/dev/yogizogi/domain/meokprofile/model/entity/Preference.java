package dev.yogizogi.domain.meokprofile.model.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Preference {

    @Min(value = 1) @Max(value = 5)
    @Schema(description = "매운맛 선호도", example = "4")
    private Long spicyPreference;

    @Min(value = 1) @Max(value = 5)
    @Schema(description = "단맛 선호도", example = "3")
    private Long sweetnessPreference;

    @Min(value = 1) @Max(value = 5)
    @Schema(description = "짠맛 선호도", example = "2")
    private Long saltyPreference;

    @Builder
    public Preference(Long spicyPreference, Long saltyPreference, Long sweetnessPreference) {
        this.spicyPreference = spicyPreference;
        this.saltyPreference = saltyPreference;
        this.sweetnessPreference = sweetnessPreference;
    }


}
