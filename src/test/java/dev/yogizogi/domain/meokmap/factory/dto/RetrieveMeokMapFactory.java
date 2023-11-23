package dev.yogizogi.domain.meokmap.factory.dto;

import dev.yogizogi.domain.meokmap.factory.fixtures.MeokMapRestaurantFixtures;
import dev.yogizogi.domain.meokmap.model.dto.response.RetrieveMeokMapOutDto;
import java.util.List;

public class RetrieveMeokMapFactory {

    public static RetrieveMeokMapOutDto retrieveMeokMapOutDto() {
        return RetrieveMeokMapOutDto.of(MeokMapRestaurantFixtures.음식점.getId(), MeokMapRestaurantFixtures.음식점.getRestaurantDetails());
    }

    public static List<RetrieveMeokMapOutDto> retrieveMeokMapOutDtos() {
        return List.of(retrieveMeokMapOutDto());
    }

}
