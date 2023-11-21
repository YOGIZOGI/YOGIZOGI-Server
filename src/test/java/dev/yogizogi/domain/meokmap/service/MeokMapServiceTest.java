package dev.yogizogi.domain.meokmap.service;

import static dev.yogizogi.global.common.model.constant.Format.DONE;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

import dev.yogizogi.domain.meokmap.exception.FailToRemoveRestaurantOnMeokMapException;
import dev.yogizogi.domain.meokmap.exception.NotExistRestaurantOnMeokMapException;
import dev.yogizogi.domain.meokmap.factory.dto.AddRestaurantOnMapFactory;
import dev.yogizogi.domain.meokmap.factory.dto.RemoveRestaurantOnMapFactory;
import dev.yogizogi.domain.meokmap.factory.entity.MeokMapFactory;
import dev.yogizogi.domain.meokmap.factory.entity.MeokMapRestaurantFactory;
import dev.yogizogi.domain.meokmap.factory.fixtures.MeokMapFixtures;
import dev.yogizogi.domain.meokmap.factory.fixtures.MeokMapRestaurantFixtures;
import dev.yogizogi.domain.meokmap.model.dto.request.AddRestaurantOnMeokMapInDto;
import dev.yogizogi.domain.meokmap.model.dto.request.RemoveRestaurantOnMeokMapInDto;
import dev.yogizogi.domain.meokmap.model.dto.response.AddRestaurantOnMeokMapOutDto;
import dev.yogizogi.domain.meokmap.model.dto.response.GetMeokMapOutDto;
import dev.yogizogi.domain.meokmap.model.entity.MeokMap;
import dev.yogizogi.domain.meokmap.model.entity.MeokMapRestaurant;
import dev.yogizogi.domain.meokmap.repository.MeokMapRepository;
import dev.yogizogi.domain.meokmap.repository.MeokMapRestaurantRepository;
import dev.yogizogi.domain.restaurant.exception.NotExistRestaurantException;
import dev.yogizogi.domain.restaurant.factory.entity.RestaurantFactory;
import dev.yogizogi.domain.restaurant.model.entity.Restaurant;
import dev.yogizogi.domain.restaurant.repository.RestaurantRepository;
import dev.yogizogi.domain.user.model.entity.User;
import dev.yogizogi.domain.user.repository.UserRepository;
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

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
@DisplayName(("MeokMapService 비즈니스 로직 동작 테스트"))
class MeokMapServiceTest {

    @InjectMocks
    private MeokMapService meokMapService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private MeokMapRepository meokMapRepository;

    @Mock
    private MeokMapRestaurantRepository meokMapRestaurantRepository;

    @Test
    void 먹지도_음식점_추가() {

        // given
        AddRestaurantOnMeokMapInDto 요청 = AddRestaurantOnMapFactory.addRestaurantOnMeokMapInDto();
        MeokMap 추가할_먹지도 = MeokMapFactory.createMeokMap();
        Restaurant 추가할_음식점 = MeokMapFixtures.음식점;

        // mocking
        given(meokMapRepository.findByUserId(eq(요청.getUserId())))
                .willReturn(Optional.of(추가할_먹지도));

        given(restaurantRepository.findById(요청.getRestaurantId()))
                .willReturn(Optional.of(추가할_음식점));

        // when
        AddRestaurantOnMeokMapOutDto 응답 = meokMapService.addRestaurantOnMeokMap(요청.getUserId(), 요청.getRestaurantId());

        // then
        Assertions.assertThat(응답.getRestaurantName()).isEqualTo(추가할_음식점.getRestaurantDetails().getName());

    }

    @Test
    void 먹지도_음식점_추가_실패_먹지도_존재하지_않는_음식점() {

        // given
        AddRestaurantOnMeokMapInDto 요청 = AddRestaurantOnMapFactory.addRestaurantOnMeokMapInDto();
        MeokMap 추가할_먹지도 = MeokMapFactory.createMeokMap();
        Restaurant 추가할_음식점 = MeokMapFixtures.음식점;

        // mocking
        given(meokMapRepository.findByUserId(eq(요청.getUserId())))
                .willReturn(Optional.empty());

        // when
        // then
        Assertions.assertThatThrownBy(
                () -> meokMapService.addRestaurantOnMeokMap(요청.getUserId(), 요청.getRestaurantId()))
                .isInstanceOf(NotExistRestaurantOnMeokMapException.class);
  
    }

    @Test
    void 먹지도_음식점_추가_실패_존재하지_않는_음식점() {

        // given
        AddRestaurantOnMeokMapInDto 요청 = AddRestaurantOnMapFactory.addRestaurantOnMeokMapInDto();
        MeokMap 추가할_먹지도 = MeokMapFactory.createMeokMap();
        Restaurant 추가할_음식점 = MeokMapFixtures.음식점;

        // mocking
        given(meokMapRepository.findByUserId(eq(요청.getUserId())))
                .willReturn(Optional.of(추가할_먹지도));

        given(restaurantRepository.findById(요청.getRestaurantId()))
                .willReturn(Optional.empty());

        // when
        // then
        Assertions.assertThatThrownBy(
                        () -> meokMapService.addRestaurantOnMeokMap(요청.getUserId(), 요청.getRestaurantId()))
                .isInstanceOf(NotExistRestaurantException.class);


    }

    @Test
    void 먹지도_음식점_삭제() {

        // given
        RemoveRestaurantOnMeokMapInDto 요청 = RemoveRestaurantOnMapFactory.removeRestaurantOnMeokMapInDto();
        MeokMap 삭제할_먹지도 = MeokMapFactory.createMeokMap();
        Restaurant 삭제할_음식점 = RestaurantFactory.createRestaurant();
        MeokMapRestaurant 삭제할_먹지도_음식점 = MeokMapRestaurantFactory.createMeokMapRestaurant();

        // mocking
        given(meokMapRepository.findByUserId(eq(요청.getUserId())))
                .willReturn(Optional.of(삭제할_먹지도));

        given(restaurantRepository.findById(요청.getRestaurantId()))
                .willReturn(Optional.of(삭제할_음식점));

        given(meokMapRestaurantRepository.findByMeokMapAndRestaurant(eq(삭제할_먹지도), eq(삭제할_음식점)))
                .willReturn(Optional.of(삭제할_먹지도_음식점));

        // when
        String 결과 = meokMapService.removeRestaurantFromMeokMap(요청.getUserId(), 요청.getRestaurantId());

        // then
        Assertions.assertThat(결과).isEqualTo(DONE);

    }

    @Test
    void 먹지도_음식점_삭제_실퍠_존재하지_않는_먹지도() {

        // given
        RemoveRestaurantOnMeokMapInDto 요청 = RemoveRestaurantOnMapFactory.removeRestaurantOnMeokMapInDto();
        MeokMap 삭제할_먹지도 = MeokMapFactory.createMeokMap();
        Restaurant 삭제할_음식점 = RestaurantFactory.createRestaurant();
        MeokMapRestaurant 삭제할_먹지도_음식점 = MeokMapRestaurantFactory.createMeokMapRestaurant();

        // mocking
        given(meokMapRepository.findByUserId(eq(요청.getUserId())))
                .willReturn(Optional.empty());

        // when
        // then
        Assertions.assertThatThrownBy(
                        () -> meokMapService.removeRestaurantFromMeokMap(요청.getUserId(), 요청.getRestaurantId()))
                .isInstanceOf(NotExistRestaurantOnMeokMapException.class);

    }

    @Test
    void 먹지도_음식점_삭제_실패_존재하지_않는_음식점() {

        // given
        RemoveRestaurantOnMeokMapInDto 요청 = RemoveRestaurantOnMapFactory.removeRestaurantOnMeokMapInDto();
        MeokMap 삭제할_먹지도 = MeokMapFactory.createMeokMap();
        Restaurant 삭제할_음식점 = RestaurantFactory.createRestaurant();
        MeokMapRestaurant 삭제할_먹지도_음식점 = MeokMapRestaurantFactory.createMeokMapRestaurant();

        // mocking
        given(meokMapRepository.findByUserId(eq(요청.getUserId())))
                .willReturn(Optional.of(삭제할_먹지도));

        given(restaurantRepository.findById(요청.getRestaurantId()))
                .willReturn(Optional.empty());

        // when
        // then
        Assertions.assertThatThrownBy(
                () -> meokMapService.removeRestaurantFromMeokMap(요청.getUserId(), 요청.getRestaurantId()))
                .isInstanceOf(NotExistRestaurantException.class);

    }

    @Test
    void 먹지도_음식점_삭제_실패_미등록한_음식점() {

        // given
        RemoveRestaurantOnMeokMapInDto 요청 = RemoveRestaurantOnMapFactory.removeRestaurantOnMeokMapInDto();
        MeokMap 삭제할_먹지도 = MeokMapFactory.createMeokMap();
        Restaurant 삭제할_음식점 = RestaurantFactory.createRestaurant();
        MeokMapRestaurant 삭제할_먹지도_음식점 = MeokMapRestaurantFactory.createMeokMapRestaurant();

        // mocking
        given(meokMapRepository.findByUserId(eq(요청.getUserId())))
                .willReturn(Optional.of(삭제할_먹지도));

        given(restaurantRepository.findById(요청.getRestaurantId()))
                .willReturn(Optional.of(삭제할_음식점));

        given(meokMapRestaurantRepository.findByMeokMapAndRestaurant(eq(삭제할_먹지도), eq(삭제할_음식점)))
                .willReturn(Optional.empty());

        // when
        // then
        Assertions.assertThatThrownBy(
                        () -> meokMapService.removeRestaurantFromMeokMap(요청.getUserId(), 요청.getRestaurantId()))
                .isInstanceOf(FailToRemoveRestaurantOnMeokMapException.class);

    }


    @Test
    void 먹지도_조회() {

        // given
        User 조회할_유저 = MeokMapFixtures.사용자;
        MeokMap 조회할_먹지도 = MeokMapRestaurantFixtures.먹지도;
        List<MeokMapRestaurant> 추가한_음식점들 = MeokMapRestaurantFactory.createMeokMapRestaurants();

        // mocking
        given(meokMapRepository.findByUserId(eq(조회할_유저.getId())))
                .willReturn(Optional.of(조회할_먹지도));

        given(meokMapRestaurantRepository.findByMeokMap(eq(조회할_먹지도)))
                .willReturn(Optional.of(추가한_음식점들));

        // when
        List<GetMeokMapOutDto> 응답 = meokMapService.getMeokMap(조회할_유저.getId());

        // then
        for (int i = 0; i < 추가한_음식점들.size(); i++) {
            Assertions.assertThat(응답.get(i).getRestaurantId()).isEqualTo(추가한_음식점들.get(i).getRestaurant().getId());
        }

    }

    @Test
    void 먹지도_조회_데이터_없음() {

        // given
        User 조회할_유저 = MeokMapFixtures.사용자;
        MeokMap 조회할_먹지도 = MeokMapRestaurantFixtures.먹지도;
        List<MeokMapRestaurant> 추가한_음식점들 = MeokMapRestaurantFactory.createMeokMapRestaurantsNoContent();

        // mocking
        given(meokMapRepository.findByUserId(eq(조회할_유저.getId())))
                .willReturn(Optional.of(조회할_먹지도));

        given(meokMapRestaurantRepository.findByMeokMap(eq(조회할_먹지도)))
                .willReturn(Optional.of(추가한_음식점들));

        // when
        List<GetMeokMapOutDto> 응답 = meokMapService.getMeokMap(조회할_유저.getId());

        // then
        Assertions.assertThat(응답).isNull();

    }

    @Test
    void 먹지도_조회_실패_등록하지_않은_음식점() {

        // given
        User 조회할_유저 = MeokMapFixtures.사용자;
        MeokMap 조회할_먹지도 = MeokMapRestaurantFixtures.먹지도;
        List<MeokMapRestaurant> 추가한_음식점들 = MeokMapRestaurantFactory.createMeokMapRestaurants();

        // mocking
        given(meokMapRepository.findByUserId(eq(조회할_유저.getId())))
                .willReturn(Optional.empty());

        // when
        // then
        Assertions.assertThatThrownBy(
                () -> meokMapService.getMeokMap(조회할_유저.getId())
        ).isInstanceOf(NotExistRestaurantOnMeokMapException.class);

    }

}