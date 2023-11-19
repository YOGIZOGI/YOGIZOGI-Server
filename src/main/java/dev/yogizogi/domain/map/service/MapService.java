package dev.yogizogi.domain.map.service;

import static dev.yogizogi.global.common.code.ErrorCode.NOT_EXIST_USER;

import dev.yogizogi.domain.map.model.dto.request.AddRestaurantOnMapOutDto;
import dev.yogizogi.domain.map.model.dto.response.AddRestaurantOnMapInDto;
import dev.yogizogi.domain.map.model.entity.Map;
import dev.yogizogi.domain.map.repository.MapRepository;
import dev.yogizogi.domain.restaurant.exception.NotExistRestaurantException;
import dev.yogizogi.domain.restaurant.model.entity.Restaurant;
import dev.yogizogi.domain.restaurant.repository.RestaurantRepository;
import dev.yogizogi.domain.user.exception.NotExistUserException;
import dev.yogizogi.domain.user.model.entity.User;
import dev.yogizogi.domain.user.repository.UserRepository;
import dev.yogizogi.global.common.code.ErrorCode;
import dev.yogizogi.global.common.status.BaseStatus;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MapService {

    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;
    private final MapRepository mapRepository;

    public AddRestaurantOnMapOutDto addRestaurantOnMap(UUID userId, UUID restaurantId) {

        User findUser = userRepository.findByIdAndStatus(userId, BaseStatus.ACTIVE)
                .orElseThrow(() -> new NotExistUserException(NOT_EXIST_USER));

        Restaurant findRestaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new NotExistRestaurantException((ErrorCode.NOT_EXIST_RESTAURANT)));

        Map map = AddRestaurantOnMapInDto.toEntity(findUser, findRestaurant);
        mapRepository.save(map);

        return AddRestaurantOnMapOutDto.of(map.getRestaurant().getRestaurantDetails().getName());

    }

}
