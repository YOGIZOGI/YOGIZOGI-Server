package dev.yogizogi.domain.review.factory.dto;

import dev.yogizogi.domain.meokprofile.factory.entity.MeokProfileVOFactory;
import dev.yogizogi.domain.review.factory.entity.MenuReviewVOFactory;
import dev.yogizogi.domain.review.factory.entity.ReviewFactory;
import dev.yogizogi.domain.review.factory.fixtures.MenuReviewFixtures;
import dev.yogizogi.domain.review.model.dto.response.GetMenuReviewOutDto;
import java.util.List;

public class GetMenuReviewFactory {

    public static GetMenuReviewOutDto getMenuReviewOutDto() {

        return GetMenuReviewOutDto.of(
                MenuReviewFixtures.메뉴_리뷰1_식별자,
                ReviewFactory.createReview().getUser().getProfile().getNickname(),
                MeokProfileVOFactory.createMeokProfileVO(),
                MenuReviewVOFactory.creatMenuReviewVO()
        );

    }

    public static GetMenuReviewOutDto getMenuReviewOutDtoNoContent() {
        return null;
    }

    public static List<GetMenuReviewOutDto> getMenuReviewOutDtos() {

        return List.of(getMenuReviewOutDto());

    }

    public static List<GetMenuReviewOutDto> getMenuReviewOutDtosNoContent() {

        List<GetMenuReviewOutDto> 응답 = List.of();
        return 응답;

    }

}
