package dev.yogizogi.domain.restaurant.model.dto.response;

import dev.yogizogi.domain.restaurant.model.entity.Information;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@Schema(name = "특정 식당 조회 응답 Dto")
public class GetRestaurantOutDto {

    @Schema(description = "음식점 식별자")
    private Long id;

    @Schema(description = "음식점에 대한 정보(이름, 주소, 좌표 등등)")
    private Information information;

    public static GetRestaurantOutDto of(Long id, Information information) {
        return GetRestaurantOutDto.builder()
                .id(id)
                .information(information)
                .build();
    }

}
