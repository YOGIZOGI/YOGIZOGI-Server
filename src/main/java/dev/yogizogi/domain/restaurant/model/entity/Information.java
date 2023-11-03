package dev.yogizogi.domain.restaurant.model.entity;

import dev.yogizogi.infra.kakao.maps.model.entity.Coordinate;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Information {

    private String name;

    private String tel;

    private String address;

    private Coordinate coordinate;

    @Builder
    public Information(String name, String tel, String address, Coordinate coordinate) {
        this.name = name;
        this.tel = tel;
        this.address = address;
        this.coordinate = coordinate;
    }

}
