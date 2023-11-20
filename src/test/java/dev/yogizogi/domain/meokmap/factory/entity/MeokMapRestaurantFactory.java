package dev.yogizogi.domain.meokmap.factory.entity;

import dev.yogizogi.domain.meokmap.factory.fixtures.MeokMapFixtures;
import dev.yogizogi.domain.meokmap.factory.fixtures.MeokMapRestaurantFixtures;
import dev.yogizogi.domain.meokmap.model.entity.MeokMapRestaurant;
import java.util.List;

public class MeokMapRestaurantFactory {

    public static MeokMapRestaurant createMeokMapRestaurant() {
        return MeokMapRestaurant.builder()
                .meokMap(MeokMapRestaurantFixtures.먹지도)
                .restaurant(MeokMapFixtures.음식점)
                .build();
    }

    public static List<MeokMapRestaurant> createMeokMapRestaurants() {
        return List.of(createMeokMapRestaurant());
    }

}
