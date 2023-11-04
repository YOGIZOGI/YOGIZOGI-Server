package dev.yogizogi.domain.restaurant.factory.dto;

import static dev.yogizogi.domain.restaurant.factory.fixtures.RestaurantFixtures.상호명;
import static dev.yogizogi.domain.restaurant.factory.fixtures.RestaurantFixtures.음식점_사진;
import static dev.yogizogi.domain.restaurant.factory.fixtures.RestaurantFixtures.전화번호;
import static dev.yogizogi.domain.restaurant.factory.fixtures.RestaurantFixtures.주소;

import dev.yogizogi.domain.restaurant.model.dto.request.CreateRestaurantInDto;

public class CreateRestaurantFactory {

    public static CreateRestaurantInDto createRestaurantInDto() {
        return new CreateRestaurantInDto(상호명, 전화번호, 주소, 음식점_사진);
    }

}
