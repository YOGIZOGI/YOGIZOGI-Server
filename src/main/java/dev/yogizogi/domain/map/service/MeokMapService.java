package dev.yogizogi.domain.map.service;

import static dev.yogizogi.global.common.code.ErrorCode.FAIL_TO_REMOVE_RESTAURANT_ON_MEOK_MAP;
import static dev.yogizogi.global.common.code.ErrorCode.NOT_EXIST_USER;
import static dev.yogizogi.global.common.model.constant.Format.DONE;

import dev.yogizogi.domain.map.exception.FailToRemoveRestaurantOnMeokMapException;
import dev.yogizogi.domain.map.model.dto.request.AddRestaurantOnMeokMapInDto;
import dev.yogizogi.domain.map.model.dto.response.AddRestaurantOnMeokMapOutDto;
import dev.yogizogi.domain.map.model.dto.response.GetMeokMapOutDto;
import dev.yogizogi.domain.map.model.entity.MeokMap;
import dev.yogizogi.domain.map.repository.MeokMapRepository;
import dev.yogizogi.domain.restaurant.exception.NotExistRestaurantException;
import dev.yogizogi.domain.restaurant.model.entity.Restaurant;
import dev.yogizogi.domain.restaurant.repository.RestaurantRepository;
import dev.yogizogi.domain.user.exception.NotExistUserException;
import dev.yogizogi.domain.user.model.entity.User;
import dev.yogizogi.domain.user.repository.UserRepository;
import dev.yogizogi.global.common.code.ErrorCode;
import dev.yogizogi.global.common.status.BaseStatus;
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

    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;
    private final MeokMapRepository meokMapRepository;

    public AddRestaurantOnMeokMapOutDto addRestaurantOnMeokMap(UUID userId, UUID restaurantId) {

        User findUser = userRepository.findByIdAndStatus(userId, BaseStatus.ACTIVE)
                .orElseThrow(() -> new NotExistUserException(NOT_EXIST_USER));

        Restaurant findRestaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new NotExistRestaurantException((ErrorCode.NOT_EXIST_RESTAURANT)));

        MeokMap meokMap = AddRestaurantOnMeokMapInDto.toEntity(findUser, findRestaurant);
        meokMapRepository.save(meokMap);

        return AddRestaurantOnMeokMapOutDto.of(meokMap.getRestaurant().getRestaurantDetails().getName());

    }

    @Transactional(readOnly = true)
    public List<GetMeokMapOutDto> getMeokMap(UUID userId) {

        User findUser = userRepository.findByIdAndStatus(userId, BaseStatus.ACTIVE)
                .orElseThrow(() -> new NotExistUserException(NOT_EXIST_USER));

        List<MeokMap> meokMap = meokMapRepository.findByUser(findUser).get();

        if (meokMap.isEmpty()) {
            return null;
        }

        return meokMap.stream()
                .map(m -> GetMeokMapOutDto.of(m.getRestaurant().getId(), m.getRestaurant().getRestaurantDetails()))
                .collect(Collectors.toList());

    }

    public String removeRestaurantFromMeokMap(UUID userId, UUID restaurantId) {

        User findUser = userRepository.findByIdAndStatus(userId, BaseStatus.ACTIVE)
                .orElseThrow(() -> new NotExistUserException(NOT_EXIST_USER));

        Restaurant findRestaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new NotExistRestaurantException((ErrorCode.NOT_EXIST_RESTAURANT)));

        MeokMap meokMap = meokMapRepository.findByUserAndRestaurant(findUser, findRestaurant)
                .orElseThrow(() -> new FailToRemoveRestaurantOnMeokMapException(FAIL_TO_REMOVE_RESTAURANT_ON_MEOK_MAP));

        meokMapRepository.delete(meokMap);

        return DONE;

    }

}
