package dev.yogizogi.domain.review.factory.dto;

import dev.yogizogi.domain.review.factory.fixtures.MenuReviewFixtures;
import dev.yogizogi.domain.review.model.dto.response.GetMenuReviewsOutDto;
import java.util.List;

public class GetMenuReviewsFactory {
    public static List<GetMenuReviewsOutDto> createMenuReviewOutDto() {

        List<GetMenuReviewsOutDto> 응답 = List.of(
                GetMenuReviewsOutDto.of(MenuReviewFixtures.작성할_메뉴, MenuReviewFixtures.메뉴_리뷰1_식별자, MenuReviewFixtures.이미지_목록, MenuReviewFixtures.메뉴_리뷰1_추천),
                GetMenuReviewsOutDto.of(MenuReviewFixtures.작성할_메뉴, MenuReviewFixtures.메뉴_리뷰2_식별자, MenuReviewFixtures.이미지_목록, MenuReviewFixtures.메뉴_리뷰2_추천)
        );

        return 응답;

    }

    public static List<GetMenuReviewsOutDto> createMenuReviewOutDtoNoContent() {
        return List.of();
    }



}
