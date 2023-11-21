package dev.yogizogi.domain.meokmap.factory.fixtures;

import dev.yogizogi.domain.restaurant.factory.entity.RestaurantFactory;
import dev.yogizogi.domain.restaurant.model.entity.Restaurant;
import dev.yogizogi.domain.user.factory.entity.UserFactory;
import dev.yogizogi.domain.user.model.entity.User;

public class MeokMapFixtures {

    public static User 사용자 = UserFactory.createUser();
    public static Restaurant 음식점 = RestaurantFactory.createRestaurant();

}
