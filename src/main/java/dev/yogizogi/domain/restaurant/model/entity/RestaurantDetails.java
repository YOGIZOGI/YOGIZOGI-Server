package dev.yogizogi.domain.restaurant.model.entity;

import dev.yogizogi.infra.kakao.maps.model.entity.Coordinate;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RestaurantDetails {

    @Schema(description = "상호명", example = "요비")
    private String name;

    @Schema(description = "전화번호", example = "0507-1399-1080")
    private String tel;

    @Schema(description = "주소", example = "서울 광진구 아차산로29길 34")
    private String address;

    @Column(columnDefinition = "TEXT")
    @Schema(description = "사진", example = "https://yogizogi-multimedia-bucket.s3.ap-northeast-2.amazonaws.com/restaurant/1/b95c3147-d7a5-4c19-9908-fb6a2cd7bdca")
    private String imageUrl;

    @Schema(description = "음식점 좌표")
    private Coordinate coordinate;

    @Builder
    public RestaurantDetails(String name, String tel, String address, String imageUrl, Coordinate coordinate) {
        this.name = name;
        this.tel = tel;
        this.address = address;
        this.imageUrl = imageUrl;
        this.coordinate = coordinate;
    }

}
