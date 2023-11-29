package dev.yogizogi.domain.restaurant.model.dto.response;

import dev.yogizogi.domain.restaurant.model.entity.RestaurantDetails;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@Schema(name = "특정 음식점 조회 응답 DTO")
public class RetrieveRestaurantsByYogiMoodsOutDto {

    @Schema(description = "음식점 식별자")
    private UUID id;

    @Schema(description = "음식점 정보")
    private RestaurantDetails restaurantDetails;

    public static RetrieveRestaurantsByYogiMoodsOutDto of(UUID id, RestaurantDetails restaurantDetails) {
        return RetrieveRestaurantsByYogiMoodsOutDto.builder()
                .id(id)
                .restaurantDetails(restaurantDetails)
                .build();
    }

}
