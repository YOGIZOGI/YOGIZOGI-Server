package dev.yogizogi.domain.meokmap.model.dto.request;

import dev.yogizogi.domain.meokmap.model.entity.MeokMap;
import dev.yogizogi.domain.user.model.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(name = "지도에 음식점 추가 요청 DTO")
public class AddRestaurantOnMeokMapInDto {

    @Schema(description = "추가할 유저")
    private UUID userId;

    @Schema(description = "추가할 음식점")
    private UUID restaurantId;

    @Builder
    public static MeokMap toEntity(User user) {
        return MeokMap.builder()
                .user(user)
                .build();
    }

}
