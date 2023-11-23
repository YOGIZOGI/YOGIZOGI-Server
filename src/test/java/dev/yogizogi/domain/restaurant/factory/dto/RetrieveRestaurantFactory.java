package dev.yogizogi.domain.restaurant.factory.dto;

import dev.yogizogi.domain.menu.factory.entity.MenuVOFactory;
import dev.yogizogi.domain.restaurant.factory.fixtures.RestaurantFixtures;
import dev.yogizogi.domain.restaurant.model.dto.response.RetrieveRestaurantOutDto;
import dev.yogizogi.domain.restaurant.model.entity.RestaurantDetails;

public class RetrieveRestaurantFactory {

    public static RetrieveRestaurantOutDto getRestaurantOutDto() {

        return RetrieveRestaurantOutDto.of(
                RestaurantFixtures.식별자,
                RestaurantDetails.builder()
                        .name(RestaurantFixtures.상호명)
                        .tel(RestaurantFixtures.전화번호)
                        .address(RestaurantFixtures.주소)
                        .imageUrl(RestaurantFixtures.음식점_사진)
                        .build(),
                MenuVOFactory.menuVOs()
        );
    }

}
