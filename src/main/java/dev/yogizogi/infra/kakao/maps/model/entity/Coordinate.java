package dev.yogizogi.infra.kakao.maps.model.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Coordinate {

    @Schema(description = "위도", example = "37.5422815915509")
    private String latitude;

    @Schema(description = "경도", example = "127.068903207917")
    private String longitude;

    @Builder
    public Coordinate(String latitude, String longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

}
