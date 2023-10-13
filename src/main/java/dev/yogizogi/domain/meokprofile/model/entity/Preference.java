package dev.yogizogi.domain.meokprofile.model.entity;

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
    private Long spicyPreference;

    @Min(value = 1) @Max(value = 5)
    private Long saltyPreference;

    @Min(value = 1) @Max(value = 5)
    private Long sweetnessPreference;

    @Builder
    public Preference(Long spicyPreference, Long saltyPreference, Long sweetnessPreference) {
        this.spicyPreference = spicyPreference;
        this.saltyPreference = saltyPreference;
        this.sweetnessPreference = sweetnessPreference;
    }


}
