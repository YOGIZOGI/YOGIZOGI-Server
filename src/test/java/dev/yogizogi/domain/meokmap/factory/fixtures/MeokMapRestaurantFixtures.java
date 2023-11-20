package dev.yogizogi.domain.meokmap.factory.fixtures;

import dev.yogizogi.domain.meokmap.factory.entity.MeokMapFactory;
import dev.yogizogi.domain.meokmap.model.entity.MeokMap;
import dev.yogizogi.domain.restaurant.factory.entity.RestaurantFactory;
import dev.yogizogi.domain.restaurant.model.entity.Restaurant;

public class MeokMapRestaurantFixtures {

    public static MeokMap 먹지도 = MeokMapFactory.createMeokMap();
    public static Restaurant 음식점 = RestaurantFactory.createRestaurant();

}
