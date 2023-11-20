package dev.yogizogi.domain.meokmap.factory.dto;

import static dev.yogizogi.domain.meokmap.factory.fixtures.MeokMapFixtures.사용자;
import static dev.yogizogi.domain.meokmap.factory.fixtures.MeokMapFixtures.음식점;

import dev.yogizogi.domain.meokmap.model.dto.request.AddRestaurantOnMeokMapInDto;
import dev.yogizogi.domain.meokmap.model.dto.response.AddRestaurantOnMeokMapOutDto;

public class AddRestaurantOnMapFactory {

    public static AddRestaurantOnMeokMapInDto addRestaurantOnMeokMapInDto() {

        return new AddRestaurantOnMeokMapInDto(사용자.getId(), 음식점.getId());

    }

    public static AddRestaurantOnMeokMapOutDto addRestaurantOnMeokMapOutDto() {

        return AddRestaurantOnMeokMapOutDto.of(음식점.getRestaurantDetails().getName());

    }

}
