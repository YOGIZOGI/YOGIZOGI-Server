package dev.yogizogi.domain.menu.factory.dto;

import static dev.yogizogi.domain.menu.factory.fixtures.MenuFixtures.가격;
import static dev.yogizogi.domain.menu.factory.fixtures.MenuFixtures.메뉴_식별자;
import static dev.yogizogi.domain.menu.factory.fixtures.MenuFixtures.설명;
import static dev.yogizogi.domain.menu.factory.fixtures.MenuFixtures.음식_사진;
import static dev.yogizogi.domain.menu.factory.fixtures.MenuFixtures.음식명;

import dev.yogizogi.domain.menu.model.dto.response.GetMenusByRestaurantOutDto;
import dev.yogizogi.domain.menu.model.entity.MenuDetails;
import java.util.ArrayList;
import java.util.List;

public class GetMenusByRestaurantFactory {

    public static List<GetMenusByRestaurantOutDto> getMenusByRestaurantOutDto() {

        List<GetMenusByRestaurantOutDto> menus = new ArrayList<>();
        menus.add(GetMenusByRestaurantOutDto.of(
                메뉴_식별자,
                MenuDetails.builder()
                        .name(음식명)
                        .price(가격)
                        .description(설명)
                        .imageUrl(음식_사진)
                        .build()
        ));

        return menus;

    }

}
