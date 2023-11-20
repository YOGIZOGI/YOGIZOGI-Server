package dev.yogizogi.domain.map.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@Schema(name = "지도에 음식점 추가 응답 Dto")
public class AddRestaurantOnMeokMapOutDto {

    @Schema(description = "추가한 음식점 이름")
    private String restaurantName;

    public static AddRestaurantOnMeokMapOutDto of(String restaurantName) {
        return AddRestaurantOnMeokMapOutDto.builder()
                .restaurantName(restaurantName)
                .build();
    }

}
