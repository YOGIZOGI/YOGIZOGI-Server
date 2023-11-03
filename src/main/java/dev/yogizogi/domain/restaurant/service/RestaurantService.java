package dev.yogizogi.domain.restaurant.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import dev.yogizogi.domain.restaurant.exception.NotExistRestaurantException;
import dev.yogizogi.domain.restaurant.model.dto.request.CreateRestaurantInDto;
import dev.yogizogi.domain.restaurant.model.dto.response.CreateRestaurantOutDto;
import dev.yogizogi.domain.restaurant.model.dto.response.GetRestaurantOutDto;
import dev.yogizogi.domain.restaurant.model.entity.Information;
import dev.yogizogi.domain.restaurant.model.entity.Restaurant;
import dev.yogizogi.domain.restaurant.repository.RestaurantRepository;
import dev.yogizogi.global.common.code.ErrorCode;
import dev.yogizogi.infra.kakao.maps.CoordinateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final CoordinateService coordinateService;

    public CreateRestaurantOutDto createRestaurant(String name, String address, String tel, String imageUrl)
            throws JsonProcessingException {

        Information information = Information.builder().name(name)
                .address(address)
                .tel(tel)
                .coordinate(coordinateService.recieveCoordinate(address))
                .build();

        Restaurant restaurant = CreateRestaurantInDto.toEntity(information, imageUrl);
        restaurantRepository.save(restaurant);

        return CreateRestaurantOutDto.of(restaurant.getId());

    }

    @Transactional(readOnly = true)
    public GetRestaurantOutDto getRestaurant(Long restaurantId) {

        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(
                () -> new NotExistRestaurantException(ErrorCode.NOT_EXIST_RESTAURANT)
        );

        return GetRestaurantOutDto.of(restaurant.getId(), restaurant.getInformation());

    }

}
