package dev.yogizogi.domain.map.model.dto.request;

import dev.yogizogi.domain.map.model.entity.MeokMap;
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
public class AddRestaurantOnMeokMapInDto {

    @Schema(description = "추가할 유저")
    private UUID userId;

    @Schema(description = "추가할 음식점")
    private UUID restaurantId;

    public static MeokMap toEntity(User user, Restaurant restaurant) {
        return MeokMap.builder()
                .user(user)
                .restaurant(restaurant)
                .build();
    }

}
