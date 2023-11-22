package dev.yogizogi.domain.meokmap.model.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(name = "지도에 음식점 삭제 요청 DTO")
public class RemoveRestaurantOnMeokMapInDto {

    @Schema(description = "유저")
    private UUID userId;

    @Schema(description = "삭제할 음식점")
    private UUID restaurantId;


}
