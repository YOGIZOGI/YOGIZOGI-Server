package dev.yogizogi.domain.restaurant.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@Schema(name = "식당 등록 응답 Dto")
public class CreateRestaurantOutDto {

    @Schema(description = "생성한 음식점 식별자", example = "1")
    private Long id;

    public static CreateRestaurantOutDto of(Long id) {
        return CreateRestaurantOutDto.builder()
                .id(id)
                .build();
    }

}
