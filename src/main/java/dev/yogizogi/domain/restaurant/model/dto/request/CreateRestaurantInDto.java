package dev.yogizogi.domain.restaurant.model.dto.request;

import dev.yogizogi.domain.restaurant.model.entity.RestaurantType;
import dev.yogizogi.domain.restaurant.model.entity.Restaurant;
import dev.yogizogi.domain.restaurant.model.entity.RestaurantDetails;
import dev.yogizogi.infra.kakao.maps.model.entity.Coordinate;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import java.util.UUID;
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

    @Schema(
            description = "판매하는 음식 종류",
            example = "[\"일식\"]",
            allowableValues = { "한식", "중식", "양식", "일식", "태국식", "베트남식", "인도식", "퓨전"}
    )
    private List<String> types;

    @Builder
    public static Restaurant toEntity(
            UUID id,
            String name, String tel, String address,
            String imageUrl,
            Coordinate coordinate,
            List<RestaurantType> types
    ) {
        return Restaurant.builder()
                .id(id)
                .restaurantDetails(
                        RestaurantDetails.builder()
                                .name(name)
                                .tel(tel)
                                .address(address)
                                .imageUrl(imageUrl)
                                .coordinate(coordinate)
                                .build()
                )
                .types(types)
                .build();
    }

}
