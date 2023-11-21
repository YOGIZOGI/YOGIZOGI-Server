package dev.yogizogi.domain.meokmap.factory.dto;

import static dev.yogizogi.domain.meokmap.factory.fixtures.MeokMapFixtures.사용자;
import static dev.yogizogi.domain.meokmap.factory.fixtures.MeokMapFixtures.음식점;

import dev.yogizogi.domain.meokmap.model.dto.request.RemoveRestaurantOnMeokMapInDto;

public class RemoveRestaurantOnMapFactory {

    public static RemoveRestaurantOnMeokMapInDto removeRestaurantOnMeokMapInDto() {
        return new RemoveRestaurantOnMeokMapInDto(사용자.getId(), 음식점.getId());
    }

}
