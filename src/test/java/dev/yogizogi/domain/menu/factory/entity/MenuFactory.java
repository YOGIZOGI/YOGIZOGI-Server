package dev.yogizogi.domain.menu.factory.entity;

import static dev.yogizogi.domain.menu.factory.fixtures.MenuFixtures.*;

import dev.yogizogi.domain.menu.model.entity.Menu;
import dev.yogizogi.domain.menu.model.entity.MenuDetails;
import dev.yogizogi.domain.review.factory.fixtures.ReviewFixtures;
import java.util.List;

public class MenuFactory {

    public static Menu createMenu() {

        Menu menu = Menu.builder()
                .restaurant(ReviewFixtures.작성할_음식점)
                .details(MenuDetails.builder()
                        .name(메뉴1_음식명)
                        .price(메뉴1_가격)
                        .description(메뉴1_설명)
                        .imageUrl(메뉴1_음식_사진)
                        .build())
                .build();

        return menu;

    }

    public static List<Menu> createMenus() {

        Menu 메뉴1 = Menu.builder()
                .restaurant(ReviewFixtures.작성할_음식점)
                .details(MenuDetails.builder()
                        .name(메뉴1_음식명)
                        .price(메뉴1_가격)
                        .description(메뉴1_설명)
                        .imageUrl(메뉴1_음식_사진)
                        .build())
                .build();

        Menu 몌뉴2 = Menu.builder()
                .restaurant(ReviewFixtures.작성할_음식점)
                .details(MenuDetails.builder()
                        .name(메뉴2_음식명)
                        .price(메뉴2_가격)
                        .description(메뉴2_설명)
                        .imageUrl(메뉴2_음식_사진)
                        .build())
                .build();

        return List.of(메뉴1, 몌뉴2);

    }

}
