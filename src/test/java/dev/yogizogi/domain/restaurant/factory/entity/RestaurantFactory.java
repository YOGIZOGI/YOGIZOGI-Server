package dev.yogizogi.domain.restaurant.factory.entity;

import static dev.yogizogi.domain.restaurant.factory.fixtures.RestaurantFixtures.경도;
import static dev.yogizogi.domain.restaurant.factory.fixtures.RestaurantFixtures.상호명;
import static dev.yogizogi.domain.restaurant.factory.fixtures.RestaurantFixtures.식별자;
import static dev.yogizogi.domain.restaurant.factory.fixtures.RestaurantFixtures.위도;
import static dev.yogizogi.domain.restaurant.factory.fixtures.RestaurantFixtures.음식점_사진;
import static dev.yogizogi.domain.restaurant.factory.fixtures.RestaurantFixtures.전화번호;
import static dev.yogizogi.domain.restaurant.factory.fixtures.RestaurantFixtures.주소;

import dev.yogizogi.domain.restaurant.model.entity.Restaurant;
import dev.yogizogi.domain.restaurant.model.entity.RestaurantDetails;
import dev.yogizogi.infra.kakao.maps.model.entity.Coordinate;

public class RestaurantFactory {

    public static Restaurant createRestaurant() {

        Restaurant restaurant = Restaurant.builder()
                .id(식별자)
                .restaurantDetails(RestaurantDetails.builder()
                        .name(상호명)
                        .tel(전화번호)
                        .address(주소)
                        .imageUrl(음식점_사진)
                        .coordinate(Coordinate.builder()
                                .latitude(위도)
                                .longitude(경도)
                                .build())
                        .build())
                .build();

        return restaurant;

    }

}
