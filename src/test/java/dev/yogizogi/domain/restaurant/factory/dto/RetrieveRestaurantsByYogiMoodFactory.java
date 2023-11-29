package dev.yogizogi.domain.restaurant.factory.dto;

import dev.yogizogi.domain.restaurant.factory.entity.RestaurantFactory;
import dev.yogizogi.domain.restaurant.model.dto.response.RetrieveRestaurantsByYogiMoodsOutDto;
import dev.yogizogi.domain.restaurant.model.entity.Restaurant;
import java.util.Collections;
import java.util.List;

public class RetrieveRestaurantsByYogiMoodFactory {

    public static RetrieveRestaurantsByYogiMoodsOutDto retrieveRestaurantsByYogiMoodsOutDto() {

        Restaurant restaurant = RestaurantFactory.createRestaurant();

        return RetrieveRestaurantsByYogiMoodsOutDto.of(
                restaurant.getId(),
                restaurant.getRestaurantDetails()
        );

    }

    public static List<RetrieveRestaurantsByYogiMoodsOutDto> retrieveRestaurantsByYogiMoodsOutDtos() {

        return List.of(RetrieveRestaurantsByYogiMoodFactory.retrieveRestaurantsByYogiMoodsOutDto());

    }

    public static List<RetrieveRestaurantsByYogiMoodsOutDto> retrieveRestaurantsByYogiMoodsOutDtosNoContent() {

        return Collections.emptyList();

    }


}
