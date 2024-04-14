package dev.yogizogi.domain.meokprofile.model.vo;

import dev.yogizogi.domain.meokprofile.model.entity.Preference;
import dev.yogizogi.domain.meokprofile.model.entity.Tag;
import dev.yogizogi.domain.user.model.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MeokProfileVO {

    @Schema(description = "먹태그")
    private List<Tag> tags;

    @Schema(description = "선호도")
    private Preference preference;

    @Builder
    public MeokProfileVO(List<Tag> tags, Preference preference) {
        this.tags = tags;
        this.preference = preference;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof MeokProfileVO that))
            return false;
        return Objects.equals(tags, that.tags) && Objects.equals(preference, that.preference);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tags, preference);
    }

}

