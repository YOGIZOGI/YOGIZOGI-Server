package dev.yogizogi.domain.restaurant.service;

import static dev.yogizogi.global.common.code.ErrorCode.INVALID_YOGI_MOOD;

import com.fasterxml.jackson.core.JsonProcessingException;
import dev.yogizogi.domain.menu.model.entity.Menu;
import dev.yogizogi.domain.menu.model.entity.MenuVO;
import dev.yogizogi.domain.restaurant.exception.InvalidRestaurantTypeException;
import dev.yogizogi.domain.restaurant.model.dto.request.CreateRestaurantInDto;
import dev.yogizogi.domain.restaurant.model.dto.response.CreateRestaurantOutDto;
import dev.yogizogi.domain.restaurant.model.dto.response.RetrieveRestaurantOutDto;
import dev.yogizogi.domain.restaurant.model.dto.response.RetrieveRestaurantsByYogiMoodsOutDto;
import dev.yogizogi.domain.restaurant.model.entity.Restaurant;
import dev.yogizogi.domain.restaurant.model.entity.RestaurantType;
import dev.yogizogi.domain.restaurant.repository.RestaurantQuerydslRepository;
import dev.yogizogi.domain.restaurant.repository.RestaurantRepository;
import dev.yogizogi.domain.review.exception.InValidYogiMoodException;
import dev.yogizogi.domain.review.model.entity.YogiMood;
import dev.yogizogi.domain.review.repository.ServiceReviewYogiMoodRepository;
import dev.yogizogi.domain.review.repository.YogiMoodRepository;
import dev.yogizogi.global.common.code.ErrorCode;
import dev.yogizogi.global.util.UuidUtils;
import dev.yogizogi.infra.kakao.maps.CoordinateService;
import dev.yogizogi.infra.kakao.maps.model.entity.Coordinate;
import java.util.Collections;
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

    private final CoordinateService coordinateService;

    private final RestaurantRepository restaurantRepository;
    private final RestaurantQuerydslRepository restaurantQuerydslRepository;
    private final ServiceReviewYogiMoodRepository serviceReviewYogiMoodRepository;
    private final YogiMoodRepository yogiMoodRepository;

    public CreateRestaurantOutDto createRestaurant
            (String name, String tel, String address, String imageUrl, List<String> typesString)
            throws JsonProcessingException {

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

    @Transactional(readOnly = true)
    public List<RetrieveRestaurantsByYogiMoodsOutDto> retrieveRestaurantsByYogiMood(List<String> yogiMoodString) {

        List<YogiMood> yogiMoods = convertToYogiMoodEntity(yogiMoodString);
        List<Restaurant> restaurants = restaurantQuerydslRepository.findRestaurantsByYogiMoods(yogiMoods).get();

        if (!hasContent(restaurants)) {
            return Collections.emptyList();
        }

        return restaurants.stream()
                .map(restaurant ->
                        RetrieveRestaurantsByYogiMoodsOutDto
                                .of(restaurant.getId(), restaurant.getRestaurantDetails())
                ).collect(Collectors.toList());

    }

    private static boolean hasContent(List<Restaurant> findRestaurants) {

        if (findRestaurants.isEmpty()) {
            return false;
        }

        return true;

    }

    /**
     * String으로 받은 YogiMood를 Entity로 변환
     */
    private List<YogiMood> convertToYogiMoodEntity(List<String> yogiMoodsString) {
        return yogiMoodsString.stream()
                .map(yogiMood ->
                        yogiMoodRepository.findByName(yogiMood)
                                .orElseThrow(() -> new InValidYogiMoodException(INVALID_YOGI_MOOD)))
                .collect(Collectors.toList());
    }

}
