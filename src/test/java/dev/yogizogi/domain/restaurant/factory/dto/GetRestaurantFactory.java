package dev.yogizogi.domain.restaurant.factory.dto;

import dev.yogizogi.domain.menu.factory.dto.GetMenusByRestaurantFactory;
import dev.yogizogi.domain.restaurant.factory.fixtures.RestaurantFixtures;
import dev.yogizogi.domain.restaurant.model.dto.response.GetRestaurantOutDto;
import dev.yogizogi.domain.restaurant.model.entity.RestaurantDetails;

public class GetRestaurantFactory {

    public static GetRestaurantOutDto getRestaurantOutDto() {

        return GetRestaurantOutDto.of(
                RestaurantFixtures.식별자,
                RestaurantDetails.builder()
                        .name(RestaurantFixtures.상호명)
                        .tel(RestaurantFixtures.전화번호)
                        .address(RestaurantFixtures.주소)
                        .imageUrl(RestaurantFixtures.음식점_사진)
                        .build(),
                GetMenusByRestaurantFactory.getMenusByRestaurantOutDto()
        );
    }

}
