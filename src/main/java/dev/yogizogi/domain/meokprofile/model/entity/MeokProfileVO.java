package dev.yogizogi.domain.meokprofile.model.entity;

import dev.yogizogi.domain.user.model.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MeokProfileVO {

    @Schema(description = "강도")
    private Intensity intensity;

    @Schema(description = "선호도")
    private Preference preference;

    @Builder
    public MeokProfileVO(User user, Intensity intensity, Preference preference) {
        this.intensity = intensity;
        this.preference = preference;
    }

}

