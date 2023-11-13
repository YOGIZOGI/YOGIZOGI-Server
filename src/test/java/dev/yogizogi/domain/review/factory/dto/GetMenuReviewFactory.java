package dev.yogizogi.domain.review.factory.dto;

import dev.yogizogi.domain.meokprofile.factory.entity.MeokProfileVOFactory;
import dev.yogizogi.domain.review.factory.entity.MenuReviewVOFactory;
import dev.yogizogi.domain.review.factory.entity.ReviewFactory;
import dev.yogizogi.domain.review.factory.fixtures.MenuReviewFixtures;
import dev.yogizogi.domain.review.model.dto.response.GetMenuReviewOutDto;
import java.util.List;

public class GetMenuReviewFactory {
    public static List<GetMenuReviewOutDto> getMenuReviewOutDto() {

        List<GetMenuReviewOutDto> 응답 = List.of(
                GetMenuReviewOutDto.of(
                        MenuReviewFixtures.메뉴_리뷰1_식별자,
                        ReviewFactory.createReview().getUser().getProfile().getNickname(),
                        MeokProfileVOFactory.createMeokProfileVO(),
                        MenuReviewVOFactory.creatMenuReviewVO()
                )
        );

        return 응답;

    }

    public static List<GetMenuReviewOutDto> getMenuReviewOutDtoNoContent() {

        List<GetMenuReviewOutDto> 응답 = List.of();
        return 응답;

    }

}
