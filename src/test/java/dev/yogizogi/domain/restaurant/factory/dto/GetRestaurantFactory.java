package dev.yogizogi.domain.restaurant.factory.dto;

import dev.yogizogi.domain.menu.factory.dto.GetMenusByRestaurantFactory;
import dev.yogizogi.domain.restaurant.model.dto.response.GetRestaurantOutDto;
import dev.yogizogi.domain.restaurant.model.entity.Restaurant;

public class GetRestaurantFactory {

    public static GetRestaurantOutDto getRestaurantOutDto(Restaurant restaurant) {

        return GetRestaurantOutDto.of(
                restaurant.getId(),
                restaurant.getRestaurantDetails(),
                GetMenusByRestaurantFactory.getMenusByRestaurantOutDto()
        );
    }

}
