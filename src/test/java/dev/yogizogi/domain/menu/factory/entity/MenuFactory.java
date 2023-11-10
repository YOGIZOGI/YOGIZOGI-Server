package dev.yogizogi.domain.menu.factory.entity;

import static dev.yogizogi.domain.menu.factory.fixtures.MenuFixtures.메뉴1_가격;
import static dev.yogizogi.domain.menu.factory.fixtures.MenuFixtures.메뉴1_설명;
import static dev.yogizogi.domain.menu.factory.fixtures.MenuFixtures.메뉴1_식별자;
import static dev.yogizogi.domain.menu.factory.fixtures.MenuFixtures.메뉴1_음식_사진;
import static dev.yogizogi.domain.menu.factory.fixtures.MenuFixtures.메뉴1_음식명;
import static dev.yogizogi.domain.menu.factory.fixtures.MenuFixtures.메뉴2_가격;
import static dev.yogizogi.domain.menu.factory.fixtures.MenuFixtures.메뉴2_설명;
import static dev.yogizogi.domain.menu.factory.fixtures.MenuFixtures.메뉴2_식별자;
import static dev.yogizogi.domain.menu.factory.fixtures.MenuFixtures.메뉴2_음식_사진;
import static dev.yogizogi.domain.menu.factory.fixtures.MenuFixtures.메뉴2_음식명;

import dev.yogizogi.domain.menu.model.entity.Menu;
import dev.yogizogi.domain.menu.model.entity.MenuDetails;
import dev.yogizogi.domain.review.factory.fixtures.ReviewFixtures;
import java.util.List;
import org.springframework.test.util.ReflectionTestUtils;

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

        ReflectionTestUtils.setField(menu, "id", 메뉴1_식별자);

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

        ReflectionTestUtils.setField(메뉴1, "id", 메뉴1_식별자);

        Menu 메뉴2 = Menu.builder()
                .restaurant(ReviewFixtures.작성할_음식점)
                .details(MenuDetails.builder()
                        .name(메뉴2_음식명)
                        .price(메뉴2_가격)
                        .description(메뉴2_설명)
                        .imageUrl(메뉴2_음식_사진)
                        .build())
                .build();

        ReflectionTestUtils.setField(메뉴2, "id", 메뉴2_식별자);

        return List.of(메뉴1, 메뉴2);

    }

}
