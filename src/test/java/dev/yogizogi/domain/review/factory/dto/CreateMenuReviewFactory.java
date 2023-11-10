package dev.yogizogi.domain.review.factory.dto;

import dev.yogizogi.domain.review.factory.fixtures.MenuReviewFixtures;
import dev.yogizogi.domain.review.factory.fixtures.ReviewFixtures;
import dev.yogizogi.domain.review.model.dto.request.CreateMenuReviewInDto;
import dev.yogizogi.domain.review.model.dto.response.CreateMenuReviewOutDto;

public class CreateMenuReviewFactory {

    public static CreateMenuReviewInDto createMenuReviewInDto() {
        return new CreateMenuReviewInDto(
                ReviewFixtures.식별자,
                MenuReviewFixtures.작성할_메뉴,
                MenuReviewFixtures.메뉴_리뷰1_내용,
                MenuReviewFixtures.메뉴_리뷰1_추천,
                MenuReviewFixtures.이미지_목록);
    }

    public static CreateMenuReviewOutDto createMenuReviewOutDto() {
        return CreateMenuReviewOutDto.of(
                MenuReviewFixtures.메뉴_리뷰1_식별자,
                ReviewFixtures.식별자,
                MenuReviewFixtures.작성할_메뉴
        );
    }
}
