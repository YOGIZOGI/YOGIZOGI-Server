package dev.yogizogi.domain.menu.service;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

import dev.yogizogi.domain.menu.factory.dto.CreateMenuFactory;
import dev.yogizogi.domain.menu.model.dto.request.CreateMenuInDto;
import dev.yogizogi.domain.menu.model.dto.response.CreateMenuOutDto;
import dev.yogizogi.domain.menu.repository.MenuRepository;
import dev.yogizogi.domain.restaurant.exception.InvalidRestaurantTypeException;
import dev.yogizogi.domain.restaurant.factory.entity.RestaurantFactory;
import dev.yogizogi.domain.restaurant.repository.RestaurantRepository;
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
@DisplayName("MenuService 비즈니스 로직 동작 테스트")
class MenuServiceTest {

    @InjectMocks
    private MenuService menuService;

    @Mock
    private MenuRepository menuRepository;

    @Mock
    private RestaurantRepository restaurantRepository;

    @Test
    void 메뉴_생성() {

        // given
        CreateMenuInDto 요청 = CreateMenuFactory.createMenuInDto();

        // mockong
        given(restaurantRepository.findById(eq(요청.getRestaurantId())))
                .willReturn(Optional.of(RestaurantFactory.createRestaurant()));

        // when
        CreateMenuOutDto 응답 = menuService.createMenu(요청.getRestaurantId(), 요청.getName(), 요청.getPrice(), 요청.getDescription(), 요청.getImageUrl());

        // then
        Assertions.assertThat(응답.getName()).isEqualTo(요청.getName());

    }

    @Test
    void 메뉴_생성_실패_존재하지_않는_음식점() {

        // given
        CreateMenuInDto 요청 = CreateMenuFactory.createMenuInDto();

        // mockong
        given(restaurantRepository.findById(eq(요청.getRestaurantId())))
                .willReturn(Optional.empty());

        // when
        // then
        Assertions.assertThatThrownBy(
                () -> menuService.createMenu(요청.getRestaurantId(), 요청.getName(), 요청.getPrice(), 요청.getDescription(), 요청.getImageUrl())
                )
                .isInstanceOf(InvalidRestaurantTypeException.class);

    }
}