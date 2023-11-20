package dev.yogizogi.domain.map.model.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@Schema(name = "지도에 음식점 삭제 요청 Dto")
public class RemoveRestaurantOnMeokMapInDto {

    @Schema(description = "유저")
    private UUID userId;

    @Schema(description = "삭제할 음식점")
    private UUID restaurantId;


}
