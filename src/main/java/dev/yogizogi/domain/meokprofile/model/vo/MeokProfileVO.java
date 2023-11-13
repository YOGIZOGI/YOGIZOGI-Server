package dev.yogizogi.domain.meokprofile.model.vo;

import dev.yogizogi.domain.meokprofile.model.entity.Intensity;
import dev.yogizogi.domain.meokprofile.model.entity.Preference;
import dev.yogizogi.domain.user.model.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Objects;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MeokProfileVO that)) {
            return false;
        }
        return intensity.equals(that.intensity) && preference.equals(that.preference);
    }

    @Override
    public int hashCode() {
        return Objects.hash(intensity, preference);
    }

}

