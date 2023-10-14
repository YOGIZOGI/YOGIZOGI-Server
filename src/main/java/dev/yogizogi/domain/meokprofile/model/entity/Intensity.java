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
public class Intensity {

    @Min(value = 1) @Max(value = 5)
    private Long spicyIntensity;

    @Min(value = 1) @Max(value = 5)
    private Long saltyIntensity;

    @Min(value = 1) @Max(value = 5)
    private Long sweetnessIntensity;

    @Builder
    public Intensity(Long spicyIntensity, Long saltyIntensity, Long sweetnessIntensity) {
        this.spicyIntensity = spicyIntensity;
        this.saltyIntensity = saltyIntensity;
        this.sweetnessIntensity = sweetnessIntensity;
    }


}
