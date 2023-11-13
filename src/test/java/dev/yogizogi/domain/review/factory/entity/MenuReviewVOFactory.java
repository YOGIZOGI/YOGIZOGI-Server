package dev.yogizogi.domain.review.factory.entity;

import dev.yogizogi.domain.review.factory.fixtures.MenuReviewFixtures;
import dev.yogizogi.domain.review.model.entity.MenuReviewImage;
import dev.yogizogi.domain.review.model.vo.MenuReviewVO;
import dev.yogizogi.global.common.status.RecommendationStatus;
import java.util.stream.Collectors;

public class MenuReviewVOFactory {

    public static MenuReviewVO creatMenuReviewVO() {

        MenuReviewVO 메뉴_리뷰 = MenuReviewVO.builder()
                .content(MenuReviewFixtures.메뉴_리뷰1_내용)
                .recommendationStatus(RecommendationStatus.valueOf(MenuReviewFixtures.메뉴_리뷰1_추천))
                .images(MenuReviewImageFactory.creatMenuReviewImage().stream()
                        .map(MenuReviewImage::getUrl).collect(Collectors.toList()))
                .build();

        return 메뉴_리뷰;

    }

}
