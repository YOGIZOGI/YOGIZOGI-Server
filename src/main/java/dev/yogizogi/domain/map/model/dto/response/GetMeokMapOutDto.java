package dev.yogizogi.domain.map.model.dto.response;

import dev.yogizogi.domain.restaurant.model.entity.RestaurantDetails;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@Schema(name = "내 지도 불러오기 응답 Dto")
public class GetMeokMapOutDto {

    @Schema(description = "음식점 식별자")
    private UUID restaurantId;

    @Schema(description = "음식점 정보")
    private RestaurantDetails restaurantDetails;

    public static GetMeokMapOutDto of(UUID restaurantId, RestaurantDetails restaurantDetails) {
        return GetMeokMapOutDto.builder()
                .restaurantId(restaurantId)
                .restaurantDetails(restaurantDetails)
                .build();
    }

}
