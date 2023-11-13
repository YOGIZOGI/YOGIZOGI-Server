package dev.yogizogi.domain.review.factory.fixtures;

import dev.yogizogi.domain.restaurant.factory.entity.RestaurantFactory;
import dev.yogizogi.domain.restaurant.model.entity.Restaurant;
import dev.yogizogi.domain.user.factory.entity.UserFactory;
import dev.yogizogi.domain.user.model.entity.User;
import dev.yogizogi.global.util.UuidUtils;
import java.util.UUID;

public class ReviewFixtures {

    public static UUID 식별자 = UuidUtils.createSequentialUUID();
    public static User 작성할_유저 = UserFactory.createUserWithProfile();
    public static Restaurant 작성할_음식점 = RestaurantFactory.createRestaurant();
    public static Restaurant 작성할_다른_음식점 = RestaurantFactory.createRestaurant();

}
