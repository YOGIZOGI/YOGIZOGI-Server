package dev.yogizogi.domain.menu.factory.dto;

import static dev.yogizogi.domain.menu.factory.fixtures.MenuFixtures.*;

import dev.yogizogi.domain.menu.model.dto.response.GetMenusByRestaurantOutDto;
import dev.yogizogi.domain.menu.model.entity.MenuDetails;
import java.util.ArrayList;
import java.util.List;

public class GetMenusByRestaurantFactory {

    public static List<GetMenusByRestaurantOutDto> getMenusByRestaurantOutDto() {

        List<GetMenusByRestaurantOutDto> menus = new ArrayList<>();

        menus.add(GetMenusByRestaurantOutDto.of(
                메뉴1_식별자,
                MenuDetails.builder()
                        .name(메뉴1_음식명)
                        .price(메뉴1_가격)
                        .description(메뉴1_설명)
                        .imageUrl(메뉴1_음식_사진)
                        .build()
        ));

        menus.add(GetMenusByRestaurantOutDto.of(
                메뉴2_식별자,
                MenuDetails.builder()
                        .name(메뉴2_음식명)
                        .price(메뉴2_가격)
                        .description(메뉴2_설명)
                        .imageUrl(메뉴2_음식_사진)
                        .build()
        ));

        return menus;

    }

}
