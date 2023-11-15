package dev.yogizogi.domain.restaurant.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import dev.yogizogi.domain.menu.model.dto.response.GetMenusByRestaurantOutDto;
import dev.yogizogi.domain.restaurant.exception.InvalidRestaurantTypeException;
import dev.yogizogi.domain.restaurant.model.dto.request.CreateRestaurantInDto;
import dev.yogizogi.domain.restaurant.model.dto.response.CreateRestaurantOutDto;
import dev.yogizogi.domain.restaurant.model.dto.response.GetRestaurantOutDto;
import dev.yogizogi.domain.restaurant.model.entity.RestaurantType;
import dev.yogizogi.domain.restaurant.model.entity.Restaurant;
import dev.yogizogi.domain.restaurant.repository.RestaurantRepository;
import dev.yogizogi.global.common.code.ErrorCode;
import dev.yogizogi.global.util.UuidUtils;
import dev.yogizogi.infra.kakao.maps.CoordinateService;
import dev.yogizogi.infra.kakao.maps.model.entity.Coordinate;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final CoordinateService coordinateService;

    public CreateRestaurantOutDto createRestaurant(String name, String tel, String address, String imageUrl,
                                                   List<String> typesString)
            throws JsonProcessingException {

        Coordinate coordinate = coordinateService.recieveCoordinate(address);

        List<RestaurantType> typesEnum = convertStringToEnum(typesString);

        Restaurant restaurant = CreateRestaurantInDto
                .toEntity(UuidUtils.createSequentialUUID(), name, tel, address, imageUrl, coordinate, typesEnum);

        restaurantRepository.save(restaurant);

        return CreateRestaurantOutDto.of(
                restaurant.getId(),
                restaurant.getRestaurantDetails().getName(),
                restaurant.getTypes()
                        .stream()
                        .map(RestaurantType::name)
                        .collect(Collectors.toList())
        );

    }

    @NotNull
    private static List<RestaurantType> convertStringToEnum(List<String> cuisines) {
        try {
            return cuisines.stream()
                    .map(RestaurantType::valueOf)
                    .collect(Collectors.toList());
        } catch (IllegalArgumentException e) {
            throw new InvalidRestaurantTypeException(ErrorCode.INVALID_RESTAURANT_TYPE);
        }
    }

    @Transactional(readOnly = true)
    public GetRestaurantOutDto getRestaurant(String name) {

        Restaurant restaurant = restaurantRepository.findRestaurantByRestaurantDetails_Name(name)
                .orElseThrow(() -> new InvalidRestaurantTypeException(ErrorCode.NOT_EXIST_RESTAURANT)
                );

        List<GetMenusByRestaurantOutDto> menus = restaurant.getMenus().stream()
                .map(menu -> GetMenusByRestaurantOutDto.of(menu.getId(), menu.getDetails()))
                .collect(Collectors.toList());

        return GetRestaurantOutDto.of(restaurant.getId(), restaurant.getRestaurantDetails(), menus);

    }

}
