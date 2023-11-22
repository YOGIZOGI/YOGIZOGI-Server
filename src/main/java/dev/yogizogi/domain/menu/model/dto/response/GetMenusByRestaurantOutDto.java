package dev.yogizogi.domain.menu.model.dto.response;

import dev.yogizogi.domain.menu.model.entity.MenuDetails;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@Schema(name = "특정 음식점 메뉴 응답 DTO")
public class GetMenusByRestaurantOutDto {

    @Schema(description = "메뉴 식별자")
    private Long menuId;

    @Schema(description = "메뉴에 대한 정보")
    private MenuDetails menuDetails;

    public static GetMenusByRestaurantOutDto of(Long menuId, MenuDetails details) {
        return GetMenusByRestaurantOutDto.builder()
                .menuId(menuId)
                .menuDetails(details)
                .build();
    }

}
