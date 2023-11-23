package dev.yogizogi.domain.restaurant.model.dto.response;

import dev.yogizogi.domain.menu.model.entity.MenuVO;
import dev.yogizogi.domain.restaurant.model.entity.RestaurantDetails;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@Schema(name = "특정 음식점 조회 응답 DTO")
public class RetrieveRestaurantOutDto {

    @Schema(description = "음식점 식별자")
    private UUID id;

    @Schema(description = "음식점에 대한 정보(이름, 주소, 좌표 등등)")
    private RestaurantDetails restaurantDetails;

    @Schema(description = "메뉴 리스트")
    private List<MenuVO> menus;

    public static RetrieveRestaurantOutDto of(UUID id, RestaurantDetails details, List<MenuVO> menus) {
        return RetrieveRestaurantOutDto.builder()
                .id(id)
                .restaurantDetails(details)
                .menus(menus)
                .build();
    }

}
