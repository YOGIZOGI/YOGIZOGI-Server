package dev.yogizogi.domain.meokmap.factory.dto;

import dev.yogizogi.domain.meokmap.factory.fixtures.MeokMapRestaurantFixtures;
import dev.yogizogi.domain.meokmap.model.dto.response.GetMeokMapOutDto;
import java.util.List;

public class GetMeokMapFactory {

    public static GetMeokMapOutDto getMeokMapOutDto() {
        return GetMeokMapOutDto.of(MeokMapRestaurantFixtures.음식점.getId(), MeokMapRestaurantFixtures.음식점.getRestaurantDetails());
    }

    public static List<GetMeokMapOutDto> getMeokMapOutDtos() {
        return List.of(getMeokMapOutDto());
    }

}
