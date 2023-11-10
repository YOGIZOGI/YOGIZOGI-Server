package dev.yogizogi.domain.menu.factory.dto;

import static dev.yogizogi.domain.menu.factory.fixtures.MenuFixtures.메뉴1_가격;
import static dev.yogizogi.domain.menu.factory.fixtures.MenuFixtures.메뉴1_설명;
import static dev.yogizogi.domain.menu.factory.fixtures.MenuFixtures.메뉴1_음식_사진;
import static dev.yogizogi.domain.menu.factory.fixtures.MenuFixtures.메뉴1_음식명;

import dev.yogizogi.domain.menu.factory.fixtures.MenuFixtures;
import dev.yogizogi.domain.menu.model.dto.request.CreateMenuInDto;
import dev.yogizogi.domain.menu.model.dto.response.CreateMenuOutDto;
import dev.yogizogi.domain.restaurant.factory.fixtures.RestaurantFixtures;

public class CreateMenuFactory {

    public static CreateMenuInDto createMenuInDto() {
        return new CreateMenuInDto(RestaurantFixtures.식별자, 메뉴1_음식명, 메뉴1_가격, 메뉴1_설명, 메뉴1_음식_사진);
    }

    public static CreateMenuOutDto createMenuOutDto() {
        return CreateMenuOutDto.of(MenuFixtures.메뉴1_식별자, 메뉴1_음식명);
    }

}
