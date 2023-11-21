package dev.yogizogi.domain.restaurant.factory.dto;

import static dev.yogizogi.domain.restaurant.factory.fixtures.RestaurantFixtures.상호명;
import static dev.yogizogi.domain.restaurant.factory.fixtures.RestaurantFixtures.식별자;
import static dev.yogizogi.domain.restaurant.factory.fixtures.RestaurantFixtures.음식점_사진;
import static dev.yogizogi.domain.restaurant.factory.fixtures.RestaurantFixtures.음식점_종류;
import static dev.yogizogi.domain.restaurant.factory.fixtures.RestaurantFixtures.잘못된_음식점_종류;
import static dev.yogizogi.domain.restaurant.factory.fixtures.RestaurantFixtures.전화번호;
import static dev.yogizogi.domain.restaurant.factory.fixtures.RestaurantFixtures.주소;

import dev.yogizogi.domain.restaurant.model.dto.request.CreateRestaurantInDto;
import dev.yogizogi.domain.restaurant.model.dto.response.CreateRestaurantOutDto;

public class CreateRestaurantFactory {

    public static CreateRestaurantInDto createRestaurantInDto() {
        return new CreateRestaurantInDto(상호명, 전화번호, 주소, 음식점_사진, 음식점_종류);
    }

    public static CreateRestaurantInDto createRestaurantInDtoInvalidRestaurantType() {
        return new CreateRestaurantInDto(상호명, 전화번호, 주소, 음식점_사진, 잘못된_음식점_종류);
    }

    public static CreateRestaurantOutDto createRestaurantOutDto() {
        return CreateRestaurantOutDto.of(식별자, 상호명, 음식점_종류);
    }

}
