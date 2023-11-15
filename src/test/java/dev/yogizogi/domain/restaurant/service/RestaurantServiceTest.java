package dev.yogizogi.domain.restaurant.service;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

import com.fasterxml.jackson.core.JsonProcessingException;
import dev.yogizogi.domain.coordinate.factory.entity.CoordinateFactory;
import dev.yogizogi.domain.menu.factory.entity.MenuFactory;
import dev.yogizogi.domain.restaurant.exception.InvalidRestaurantTypeException;
import dev.yogizogi.domain.restaurant.factory.dto.CreateRestaurantFactory;
import dev.yogizogi.domain.restaurant.factory.entity.RestaurantFactory;
import dev.yogizogi.domain.restaurant.model.dto.request.CreateRestaurantInDto;
import dev.yogizogi.domain.restaurant.model.dto.response.CreateRestaurantOutDto;
import dev.yogizogi.domain.restaurant.model.dto.response.GetRestaurantOutDto;
import dev.yogizogi.domain.restaurant.model.entity.Restaurant;
import dev.yogizogi.domain.restaurant.repository.RestaurantRepository;
import dev.yogizogi.infra.kakao.maps.CoordinateService;
import dev.yogizogi.infra.kakao.maps.model.entity.Coordinate;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.util.ReflectionTestUtils;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
@DisplayName("RestaurantService 비즈니스 로직 동작 테스트")
class RestaurantServiceTest {

    @InjectMocks
    private RestaurantService restaurantService;

    @Mock
    private CoordinateService coordinateService;

    @Mock
    private RestaurantRepository restaurantRepository;

    @Test
    void 음식점_생성() throws JsonProcessingException {

        // given
        CreateRestaurantInDto 요청 = CreateRestaurantFactory.createRestaurantInDto();
        Coordinate 좌표 = CoordinateFactory.createCoordinate();

        // mocking
        given(coordinateService.recieveCoordinate(eq(요청.getAddress()))).willReturn(좌표);

        // when
        CreateRestaurantOutDto 응답 = restaurantService
                .createRestaurant(요청.getName(), 요청.getTel(), 요청.getAddress(), 요청.getImageUrl(), 요청.getTypes());

        // then
        Assertions.assertThat(응답.getName()).isEqualTo(요청.getName());
        Assertions.assertThat(응답.getTypes().get(0)).isEqualTo(요청.getTypes().get(0));

    }

    @Test
    void 음식점_생성_실패_유효하지_않은_음식점_종류() throws JsonProcessingException {

        // given
        CreateRestaurantInDto 요청 = CreateRestaurantFactory.createRestaurantInDtoInvalidRestaurantType();
        Coordinate 좌표 = CoordinateFactory.createCoordinate();

        // mocking
        given(coordinateService.recieveCoordinate(eq(요청.getAddress()))).willReturn(좌표);

        // when
        // then
        Assertions.assertThatThrownBy(
                () -> restaurantService
                        .createRestaurant(요청.getName(), 요청.getTel(), 요청.getAddress(), 요청.getImageUrl(), 요청.getTypes())
        ).isInstanceOf(InvalidRestaurantTypeException.class);

    }

    @Test
    void 특정_음식점_조회() {

        // given
        String 조회할_음식점_상호명 = "요비";
        Restaurant 조회할_음식점 = RestaurantFactory.createRestaurant();
        
        // mocking
        ReflectionTestUtils.setField(조회할_음식점, "menus", MenuFactory.createMenus());
        given(restaurantRepository.findRestaurantByRestaurantDetails_Name(eq(조회할_음식점_상호명)))
                .willReturn(Optional.of(조회할_음식점));

        // when
        GetRestaurantOutDto 응답 = restaurantService.getRestaurant(조회할_음식점_상호명);

        // then
        Assertions.assertThat(응답.getRestaurantDetails().getName()).isEqualTo(조회할_음식점_상호명);
        Assertions.assertThat(응답.getMenus().size()).isEqualTo(2);

    }

    @Test
    void 특정_음식점_조회_실패_존재하지_않는_음식() {

        // given
        String 조회할_음식점_상호명 = "요비";

        // mocking
        given(restaurantRepository.findRestaurantByRestaurantDetails_Name(eq(조회할_음식점_상호명)))
                .willReturn(Optional.empty());

        // when
        // then
        Assertions.assertThatThrownBy(
                () -> restaurantService.getRestaurant(조회할_음식점_상호명))
                .isInstanceOf(InvalidRestaurantTypeException.class);
    }


}