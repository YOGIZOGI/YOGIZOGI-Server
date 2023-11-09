package dev.yogizogi.domain.menu.factory.dto;

import static dev.yogizogi.domain.menu.factory.fixtures.MenuFixtures.가격;
import static dev.yogizogi.domain.menu.factory.fixtures.MenuFixtures.설명;
import static dev.yogizogi.domain.menu.factory.fixtures.MenuFixtures.음식_사진;
import static dev.yogizogi.domain.menu.factory.fixtures.MenuFixtures.음식명;

import dev.yogizogi.domain.menu.model.dto.request.CreateMenuInDto;
import dev.yogizogi.domain.menu.model.dto.response.CreateMenuOutDto;
import dev.yogizogi.domain.restaurant.factory.fixtures.RestaurantFixtures;

public class CreateMenuFactory {

    public static CreateMenuInDto createMenuInDto() {
        return new CreateMenuInDto(RestaurantFixtures.식별자, 음식명, 가격, 설명, 음식_사진);
    }

    public static CreateMenuOutDto createMenuOutDto() {
        return CreateMenuOutDto.of(1L, 음식명);
    }

}
