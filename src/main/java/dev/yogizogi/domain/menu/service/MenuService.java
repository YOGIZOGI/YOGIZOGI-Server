package dev.yogizogi.domain.menu.service;

import dev.yogizogi.domain.menu.model.dto.request.CreateMenuInDto;
import dev.yogizogi.domain.menu.model.dto.response.CreateMenuOutDto;
import dev.yogizogi.domain.menu.model.entity.Menu;
import dev.yogizogi.domain.menu.repository.MenuRepository;
import dev.yogizogi.domain.restaurant.exception.NotExistRestaurantException;
import dev.yogizogi.domain.restaurant.model.entity.Restaurant;
import dev.yogizogi.domain.restaurant.repository.RestaurantRepository;
import dev.yogizogi.global.common.code.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;
    private final RestaurantRepository restaurantRepository;

    public CreateMenuOutDto createMenu(CreateMenuInDto req) {

        Restaurant restaurant = restaurantRepository.findById(req.getRestaurantId())
                .orElseThrow(() -> new NotExistRestaurantException(ErrorCode.NOT_EXIST_RESTAURANT));

        Menu menu = CreateMenuInDto.toEntity(req);
        restaurant.addMenu(menu);

        menuRepository.save(menu);

        return CreateMenuOutDto.of(menu.getId());

    }

}
