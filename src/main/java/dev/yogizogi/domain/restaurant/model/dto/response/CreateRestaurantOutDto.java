package dev.yogizogi.domain.restaurant.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@Schema(name = "음식점 등록 응답 Dto")
public class CreateRestaurantOutDto {

    @Schema(description = "생성한 음식점 식별자")
    private UUID id;

    @Schema(description = "생성한 음식점 상호명", example = "요비")
    private String name;

    @Schema(description = "생성한 음식점 종류", example = "일식")
    private List<String> types;

    public static CreateRestaurantOutDto of(UUID id, String name, List<String> types) {
        return CreateRestaurantOutDto.builder()
                .id(id)
                .name(name)
                .types(types)
                .build();
    }

}
