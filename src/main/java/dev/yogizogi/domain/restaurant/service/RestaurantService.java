package dev.yogizogi.domain.restaurant.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import dev.yogizogi.domain.restaurant.exception.NotExistRestaurantException;
import dev.yogizogi.domain.restaurant.model.dto.request.CreateRestaurantInDto;
import dev.yogizogi.domain.restaurant.model.dto.response.CreateRestaurantOutDto;
import dev.yogizogi.domain.restaurant.model.dto.response.GetRestaurantOutDto;
import dev.yogizogi.domain.restaurant.model.entity.Restaurant;
import dev.yogizogi.domain.restaurant.repository.RestaurantRepository;
import dev.yogizogi.global.common.code.ErrorCode;
import dev.yogizogi.infra.kakao.maps.CoordinateService;
import dev.yogizogi.infra.kakao.maps.model.entity.Coordinate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final CoordinateService coordinateService;

    public CreateRestaurantOutDto createRestaurant(CreateRestaurantInDto req)
            throws JsonProcessingException {

        Coordinate coordinate = coordinateService.recieveCoordinate(req.getAddress());
        Restaurant restaurant = CreateRestaurantInDto.toEntity(req, coordinate);
        restaurantRepository.save(restaurant);

        return CreateRestaurantOutDto.of(restaurant.getId());

    }

    @Transactional(readOnly = true)
    public GetRestaurantOutDto getRestaurant(Long restaurantId) {

        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(
                () -> new NotExistRestaurantException(ErrorCode.NOT_EXIST_RESTAURANT)
        );

        return GetRestaurantOutDto.of(restaurant.getId(), restaurant.getDetails());

    }

}
