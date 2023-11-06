package dev.yogizogi.domain.coordinate.factory.entity;

import static dev.yogizogi.domain.restaurant.factory.fixtures.RestaurantFixtures.경도;
import static dev.yogizogi.domain.restaurant.factory.fixtures.RestaurantFixtures.위도;

import dev.yogizogi.infra.kakao.maps.model.entity.Coordinate;

public class CoordinateFactory {

    public static Coordinate createCoordinate() {

        Coordinate coordinate = Coordinate.builder()
                .latitude(위도)
                .longitude(경도)
                .build();

        return coordinate;

    }

}
