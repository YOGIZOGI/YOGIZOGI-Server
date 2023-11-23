package dev.yogizogi.domain.meokmap.factory.dto;

import dev.yogizogi.domain.meokmap.factory.fixtures.MeokMapRestaurantFixtures;
import dev.yogizogi.domain.meokmap.model.dto.response.RetrieveMeokMapOutDto;
import java.util.List;

public class GetMeokMapFactory {

    public static RetrieveMeokMapOutDto getMeokMapOutDto() {
        return RetrieveMeokMapOutDto.of(MeokMapRestaurantFixtures.음식점.getId(), MeokMapRestaurantFixtures.음식점.getRestaurantDetails());
    }

    public static List<RetrieveMeokMapOutDto> getMeokMapOutDtos() {
        return List.of(getMeokMapOutDto());
    }

}
