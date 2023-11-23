package dev.yogizogi.domain.menu.model.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MenuVO {

    @Schema(description = "메뉴 식별자")
    private Long id;

    @Schema(description = "메뉴에 대한 정보")
    private MenuDetails details;
    @Builder
    public MenuVO(Long id, MenuDetails details) {
        this.id = id;
        this.details = details;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MenuVO menuVO)) {
            return false;
        }
        return Objects.equals(id, menuVO.id) && Objects.equals(details, menuVO.details);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, details);
    }

}

