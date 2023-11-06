package dev.yogizogi.domain.menu.factory.entity;

import static dev.yogizogi.domain.menu.factory.fixtures.MenuFixtures.가격;
import static dev.yogizogi.domain.menu.factory.fixtures.MenuFixtures.설명;
import static dev.yogizogi.domain.menu.factory.fixtures.MenuFixtures.음식_사진;
import static dev.yogizogi.domain.menu.factory.fixtures.MenuFixtures.음식명;

import dev.yogizogi.domain.menu.model.entity.Menu;
import dev.yogizogi.domain.menu.model.entity.MenuDetails;

public class MenuFactory {

    public static Menu createMenu() {

        Menu menu = Menu.builder()
                .details(MenuDetails.builder()
                        .name(음식명)
                        .price(가격)
                        .description(설명)
                        .imageUrl(음식_사진)
                        .build())
                .build();

        return menu;

    }

}
