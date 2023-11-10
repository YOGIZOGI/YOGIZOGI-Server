package dev.yogizogi.domain.review.factory.dto;

import dev.yogizogi.domain.review.factory.fixtures.MenuReviewFixtures;
import dev.yogizogi.domain.review.model.dto.response.GetMenuReviewOutDto;

public class GetMenuReviewFactory {
    public static GetMenuReviewOutDto getMenuReviewOutDto() {

        return GetMenuReviewOutDto.of(MenuReviewFixtures.메뉴_리뷰1_식별자, MenuReviewFixtures.메뉴_리뷰1_내용);

    }

}
