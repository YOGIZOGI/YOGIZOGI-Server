package dev.yogizogi.domain.review.factory.dto;

import dev.yogizogi.domain.menu.factory.fixtures.MenuFixtures;
import dev.yogizogi.domain.review.model.dto.response.GetMenuReviewsOutDto;

public class GetMenuReviewsFactory {
    public static GetMenuReviewsOutDto createMenuReviewOutDto() {

        GetMenuReviewsOutDto 응답 = GetMenuReviewsOutDto.of(
                MenuFixtures.메뉴1_식별자,
                GetMenuReviewFactory.getMenuReviewOutDto()
        );

        return 응답;

    }

    public static GetMenuReviewsOutDto createMenuReviewOutDtoNoContent() {
        return null;
    }



}
