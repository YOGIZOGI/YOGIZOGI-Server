package dev.yogizogi.domain.restaurant.service;

import static dev.yogizogi.domain.review.factory.fixtures.ServiceReviewFixtures.요기무드;
import static dev.yogizogi.domain.review.factory.fixtures.ServiceReviewFixtures.유효하지_않은_요기무드;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

import com.fasterxml.jackson.core.JsonProcessingException;
import dev.yogizogi.domain.coordinate.factory.entity.CoordinateFactory;
import dev.yogizogi.domain.menu.factory.entity.MenuFactory;
import dev.yogizogi.domain.menu.model.entity.Menu;
import dev.yogizogi.domain.restaurant.exception.InvalidRestaurantTypeException;
import dev.yogizogi.domain.restaurant.factory.dto.CreateRestaurantFactory;
import dev.yogizogi.domain.restaurant.factory.entity.RestaurantFactory;
import dev.yogizogi.domain.restaurant.model.dto.request.CreateRestaurantInDto;
import dev.yogizogi.domain.restaurant.model.dto.response.CreateRestaurantOutDto;
import dev.yogizogi.domain.restaurant.model.dto.response.RetrieveRestaurantOutDto;
import dev.yogizogi.domain.restaurant.model.dto.response.RetrieveRestaurantsByYogiMoodsOutDto;
import dev.yogizogi.domain.restaurant.model.entity.Restaurant;
import dev.yogizogi.domain.restaurant.repository.RestaurantQuerydslRepository;
import dev.yogizogi.domain.restaurant.repository.RestaurantRepository;
import dev.yogizogi.domain.review.exception.InValidYogiMoodException;
import dev.yogizogi.domain.review.factory.entity.YogiMoodFactory;
import dev.yogizogi.domain.review.model.entity.YogiMood;
import dev.yogizogi.domain.review.repository.ServiceReviewYogiMoodRepository;
import dev.yogizogi.domain.review.repository.YogiMoodRepository;
import dev.yogizogi.infra.kakao.maps.CoordinateService;
import dev.yogizogi.infra.kakao.maps.model.entity.Coordinate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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

    @Mock
    private RestaurantQuerydslRepository restaurantQuerydslRepository;

    @Mock
    private ServiceReviewYogiMoodRepository serviceReviewYogiMoodRepository;

    @Mock
    private YogiMoodRepository yogiMoodRepository;



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
        Restaurant 조회할_음식점 = RestaurantFactory.createRestaurant();
        List<Menu> 조회할_음식점_메뉴 = MenuFactory.createMenus();

        // mocking
        ReflectionTestUtils.setField(조회할_음식점, "menus", 조회할_음식점_메뉴);

        given(restaurantRepository.findById(eq(조회할_음식점.getId())))
                .willReturn(Optional.of(조회할_음식점));

        // when
        RetrieveRestaurantOutDto 응답 = restaurantService.retrieveRestaurant(조회할_음식점.getId());

        // then
        Assertions.assertThat(응답.getRestaurantDetails().getName()).isEqualTo(조회할_음식점.getRestaurantDetails().getName());
        Assertions.assertThat(응답.getMenus().size()).isEqualTo(2);

    }

    @Test
    void 특정_음식점_조회_실패_존재하지_않는_음식점() {

        // given
        Restaurant 조회할_음식점 = RestaurantFactory.createRestaurant();

        // mocking
        given(restaurantRepository.findById(eq(조회할_음식점.getId())))
                .willReturn(Optional.empty());

        // when
        // then
        Assertions
                .assertThatThrownBy(() -> restaurantService.retrieveRestaurant(조회할_음식점.getId()))
                .isInstanceOf(InvalidRestaurantTypeException.class);

    }

    @Test
    void 특정_요기무드_포함_음식점_조회() {

        // given
        List<String> 조회할_요기무드_문자열 = 요기무드;
        List<YogiMood> 조회할_요기무드_객체 =  new ArrayList<>();
        for (String y : 조회할_요기무드_문자열) {
            조회할_요기무드_객체.add(YogiMoodFactory.createYogiMood(y));
        }
        List<Restaurant> 포함된_음식점 = RestaurantFactory.createRestaurants();

        // mocking
        for (String y : 조회할_요기무드_문자열) {
            given(yogiMoodRepository.findByName(eq(y))).willReturn(Optional.of(YogiMoodFactory.createYogiMood(y)));
        }

        given(restaurantQuerydslRepository.findRestaurantsByYogiMoods(anyList()))
                .willReturn(Optional.of(포함된_음식점));

        // when
        List<RetrieveRestaurantsByYogiMoodsOutDto> 응답
                = restaurantService.retrieveRestaurantsByYogiMood(조회할_요기무드_문자열);

        // then
        for (int i = 0; i < 응답.size(); i++) {

            Assertions.assertThat(응답.get(i).getId())
                    .isEqualTo(포함된_음식점.get(i).getId());

            Assertions.assertThat(응답.get(i).getRestaurantDetails().getName())
                    .isEqualTo(포함된_음식점.get(i).getRestaurantDetails().getName());

        }


    }

    @Test
    void 특정_요기무드_포함_음식점_조회_데이터_없음() {

        // given
        List<String> 조회할_요기무드_문자열 = 요기무드;
        List<YogiMood> 조회할_요기무드_객체 =  new ArrayList<>();
        for (String y : 조회할_요기무드_문자열) {
            조회할_요기무드_객체.add(YogiMoodFactory.createYogiMood(y));
        }
        List<Restaurant> 포함된_음식점 = Collections.emptyList();

        // mocking
        for (String y : 조회할_요기무드_문자열) {
            given(yogiMoodRepository.findByName(eq(y))).willReturn(Optional.of(YogiMoodFactory.createYogiMood(y)));
        }

        given(restaurantQuerydslRepository.findRestaurantsByYogiMoods(anyList()))
                .willReturn(Optional.of(포함된_음식점));

        // when
        List<RetrieveRestaurantsByYogiMoodsOutDto> 응답
                = restaurantService.retrieveRestaurantsByYogiMood(조회할_요기무드_문자열);

        // then
        Assertions.assertThat(응답).isEmpty();

    }


    @Test
    void 특정_요기무드_포함_음식점_조회_실패_유효하지_않은_요기무드() {

        // given
        List<String> 등록할_요기무드 = 유효하지_않은_요기무드;

        // mocking
        // when
        // then
        Assertions.assertThatThrownBy(
                () -> restaurantService.retrieveRestaurantsByYogiMood(등록할_요기무드)
        ).isInstanceOf(InValidYogiMoodException.class);

    }


}