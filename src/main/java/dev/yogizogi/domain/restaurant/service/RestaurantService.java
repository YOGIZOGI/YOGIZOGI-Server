package dev.yogizogi.domain.restaurant.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import dev.yogizogi.domain.menu.model.entity.Menu;
import dev.yogizogi.domain.menu.model.entity.MenuVO;
import dev.yogizogi.domain.restaurant.exception.InvalidRestaurantTypeException;
import dev.yogizogi.domain.restaurant.model.dto.request.CreateRestaurantInDto;
import dev.yogizogi.domain.restaurant.model.dto.response.CreateRestaurantOutDto;
import dev.yogizogi.domain.restaurant.model.dto.response.RetrieveRestaurantOutDto;
import dev.yogizogi.domain.restaurant.model.entity.Restaurant;
import dev.yogizogi.domain.restaurant.model.entity.RestaurantType;
import dev.yogizogi.domain.restaurant.repository.RestaurantRepository;
import dev.yogizogi.global.common.code.ErrorCode;
import dev.yogizogi.global.util.UuidUtils;
import dev.yogizogi.infra.kakao.maps.CoordinateService;
import dev.yogizogi.infra.kakao.maps.model.entity.Coordinate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final CoordinateService coordinateService;

    public CreateRestaurantOutDto createRestaurant
            (String name, String tel, String address, String imageUrl, List<String> typesString) throws JsonProcessingException {

        Coordinate coordinate = coordinateService.recieveCoordinate(address);

        List<RestaurantType> typesEnum = convertToRestaurantTypeEnum(typesString);

        Restaurant restaurant = CreateRestaurantInDto
                .toEntity(UuidUtils.createSequentialUUID(), name, tel, address, imageUrl, coordinate, typesEnum);

        restaurantRepository.save(restaurant);

        return CreateRestaurantOutDto.of(
                restaurant.getId(),
                restaurant.getRestaurantDetails().getName(),
                convertToRestaurantTypeString(restaurant.getTypes())
        );

    }

    private List<String> convertToRestaurantTypeString(List<RestaurantType> restaurantTypes) {
        return restaurantTypes.stream()
                .map(RestaurantType::name)
                .collect(Collectors.toList());
    }

    private static List<RestaurantType> convertToRestaurantTypeEnum(List<String> restaurantTypes) {
        try {
            return restaurantTypes.stream()
                    .map(RestaurantType::valueOf)
                    .collect(Collectors.toList());
        } catch (IllegalArgumentException e) {
            throw new InvalidRestaurantTypeException(ErrorCode.INVALID_RESTAURANT_TYPE);
        }
    }

    @Transactional(readOnly = true)
    public RetrieveRestaurantOutDto retrieveRestaurant(UUID restaurantId) {

        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new InvalidRestaurantTypeException(ErrorCode.NOT_EXIST_RESTAURANT));

        List<MenuVO> menus = convertToMenuVO(restaurant.getMenus());

        return RetrieveRestaurantOutDto.of(restaurant.getId(), restaurant.getRestaurantDetails(), menus);

    }

    private List<MenuVO> convertToMenuVO(List<Menu> menus) {
        return menus.stream()
                .map(menu ->
                        MenuVO.builder()
                                .id(menu.getId())
                                .details(menu.getDetails())
                                .build()
                ).collect(Collectors.toList());
    }

}
