package dev.yogizogi.domain.restaurant.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@Schema(name = "식당 등록 응답 Dto")
public class CreateRestaurantOutDto {

    @Schema(description = "생성한 음식점 상호명", example = "요비")
    private String name;

    public static CreateRestaurantOutDto of(String name) {
        return CreateRestaurantOutDto.builder()
                .name(name)
                .build();
    }

}
