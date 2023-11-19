package dev.yogizogi.domain.map.model.dto.response;

import dev.yogizogi.domain.map.model.entity.Map;
import dev.yogizogi.domain.restaurant.model.entity.Restaurant;
import dev.yogizogi.domain.user.model.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@Schema(name = "지도에 음식점 추가 요청 Dto")
public class AddRestaurantOnMapInDto {

    private UUID userId;

    private UUID restaurantId;

    public static Map toEntity(User user, Restaurant restaurant) {
        return Map.builder()
                .user(user)
                .restaurant(restaurant)
                .build();
    }

}
