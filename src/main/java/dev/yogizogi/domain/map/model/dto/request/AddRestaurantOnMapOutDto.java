package dev.yogizogi.domain.map.model.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@Schema(name = "지도에 음식점 추가 응답 Dto")
public class AddRestaurantOnMapOutDto {

    private String restaurantName;

    public static AddRestaurantOnMapOutDto of(String restaurantName) {
        return AddRestaurantOnMapOutDto.builder()
                .restaurantName(restaurantName)
                .build();
    }

}
