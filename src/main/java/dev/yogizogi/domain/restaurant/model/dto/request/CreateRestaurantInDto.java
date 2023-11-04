package dev.yogizogi.domain.restaurant.model.dto.request;

import dev.yogizogi.domain.restaurant.model.entity.RestaurantDetails;
import dev.yogizogi.domain.restaurant.model.entity.Restaurant;
import dev.yogizogi.infra.kakao.maps.model.entity.Coordinate;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(name = "식당 등록 요청 Dto")
public class CreateRestaurantInDto {

    @Schema(description = "이름", example = "요비")
    private String name;

    @Schema(description = "전화번호", example = "0507-1399-1080")
    private String tel;

    @Schema(description = "주소", example = "서울 광진구 아차산로29길 34")
    private String address;

    @Schema(description = "음식점 외관 사진", example = "https://yogizogi-multimedia-bucket.s3.ap-northeast-2.amazonaws.com/restaurant/1/b95c3147-d7a5-4c19-9908-fb6a2cd7bdca")
    private String imageUrl;

    @Builder
    public static Restaurant toEntity(CreateRestaurantInDto req, Coordinate coordinate) {
        return Restaurant.builder()
                .details(
                        RestaurantDetails.builder()
                                .name(req.getName())
                                .address(req.getAddress())
                                .tel(req.getAddress())
                                .coordinate(coordinate)
                                .build()
                )
                .imageUrl(req.getImageUrl())
                .build();
    }

}
