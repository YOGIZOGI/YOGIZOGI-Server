package dev.yogizogi.domain.meokmap.service;

import static dev.yogizogi.global.common.code.ErrorCode.FAIL_TO_REMOVE_RESTAURANT_ON_MEOK_MAP;
import static dev.yogizogi.global.common.code.ErrorCode.NOT_EXIST_MEOK_MAP;
import static dev.yogizogi.global.common.code.ErrorCode.NOT_EXIST_RESTAURANT_ON_MEOK_MAP;
import static dev.yogizogi.global.common.model.constant.Format.DONE;

import dev.yogizogi.domain.meokmap.exception.FailToRemoveRestaurantOnMeokMapException;
import dev.yogizogi.domain.meokmap.exception.NotExistMeokMapException;
import dev.yogizogi.domain.meokmap.exception.NotExistRestaurantOnMeokMapException;
import dev.yogizogi.domain.meokmap.model.dto.response.AddRestaurantOnMeokMapOutDto;
import dev.yogizogi.domain.meokmap.model.dto.response.RetrieveMeokMapOutDto;
import dev.yogizogi.domain.meokmap.model.entity.MeokMap;
import dev.yogizogi.domain.meokmap.model.entity.MeokMapRestaurant;
import dev.yogizogi.domain.meokmap.repository.MeokMapRepository;
import dev.yogizogi.domain.meokmap.repository.MeokMapRestaurantRepository;
import dev.yogizogi.domain.restaurant.exception.NotExistRestaurantException;
import dev.yogizogi.domain.restaurant.model.entity.Restaurant;
import dev.yogizogi.domain.restaurant.repository.RestaurantRepository;
import dev.yogizogi.global.common.code.ErrorCode;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MeokMapService {

    private final RestaurantRepository restaurantRepository;
    private final MeokMapRepository meokMapRepository;
    private final MeokMapRestaurantRepository meokMapRestaurantRepository;

    public AddRestaurantOnMeokMapOutDto addRestaurantOnMeokMap(UUID userId, UUID restaurantId) {

        MeokMap findMeokMap = meokMapRepository.findByUserId(userId)
                .orElseThrow(() -> new NotExistMeokMapException(NOT_EXIST_MEOK_MAP));

        Restaurant findRestaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new NotExistRestaurantException((ErrorCode.NOT_EXIST_RESTAURANT)));


        MeokMapRestaurant meokMapRestaurant = MeokMapRestaurant.builder()
                .meokMap(findMeokMap)
                .restaurant(findRestaurant)
                .build();

        meokMapRestaurantRepository.save(meokMapRestaurant);

        return AddRestaurantOnMeokMapOutDto.of(meokMapRestaurant.getRestaurant().getRestaurantDetails().getName());

    }

    public String removeRestaurantFromMeokMap(UUID userId, UUID restaurantId) {

        MeokMap findMeokMap = meokMapRepository.findByUserId(userId)
                .orElseThrow(() -> new NotExistRestaurantOnMeokMapException(NOT_EXIST_RESTAURANT_ON_MEOK_MAP));

        Restaurant findRestaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new NotExistRestaurantException((ErrorCode.NOT_EXIST_RESTAURANT)));

        MeokMapRestaurant meokMapRestaurant = meokMapRestaurantRepository.findByMeokMapAndRestaurant(findMeokMap, findRestaurant)
                .orElseThrow(() -> new FailToRemoveRestaurantOnMeokMapException(FAIL_TO_REMOVE_RESTAURANT_ON_MEOK_MAP));

        meokMapRestaurantRepository.delete(meokMapRestaurant);

        return DONE;

    }

    @Transactional(readOnly = true)
    public List<RetrieveMeokMapOutDto> retrieveMeokMap(UUID userId) {

        MeokMap meokMap = meokMapRepository.findByUserId(userId)
                .orElseThrow(() -> new NotExistMeokMapException(NOT_EXIST_MEOK_MAP));

        List<MeokMapRestaurant> meokMapRestaurants = meokMapRestaurantRepository.findByMeokMap(meokMap).get();

        if (meokMapRestaurants.isEmpty()) {
            return null;
        }

        return meokMapRestaurants.stream()
                .map(m -> RetrieveMeokMapOutDto.of(m.getRestaurant().getId(), m.getRestaurant().getRestaurantDetails()))
                .collect(Collectors.toList());

    }

}
