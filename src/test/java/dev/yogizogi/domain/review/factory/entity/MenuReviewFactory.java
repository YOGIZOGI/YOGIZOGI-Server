package dev.yogizogi.domain.review.factory.entity;

import dev.yogizogi.domain.menu.factory.entity.MenuFactory;
import dev.yogizogi.domain.review.factory.fixtures.MenuReviewFixtures;
import dev.yogizogi.domain.review.model.entity.MenuReview;
import dev.yogizogi.global.common.status.RecommendationStatus;
import java.util.List;
import org.springframework.test.util.ReflectionTestUtils;

public class MenuReviewFactory {

    public static MenuReview creatMenuReview() {

        MenuReview 메뉴_리뷰 = MenuReview.builder()
                .menu(MenuFactory.createMenu())
                .review(ReviewFactory.createReview())
                .menu(MenuFactory.createMenu())
                .content(MenuReviewFixtures.메뉴_리뷰1_내용)
                .recommendationStatus(RecommendationStatus.valueOf(MenuReviewFixtures.메뉴_리뷰1_추천))
                .build();

        ReflectionTestUtils.setField(메뉴_리뷰, "id", MenuReviewFixtures.메뉴_리뷰1_식별자);;

        return 메뉴_리뷰;

    }

    public static List<MenuReview> creatMenuReviews() {

        List<MenuReview> 조회할_메뉴_리뷰 = List.of(
                creatMenuReview()
        );

        for (MenuReview menuReview : 조회할_메뉴_리뷰) {
            MenuReviewImageFactory.creatMenuReviewImage();
        }

        return 조회할_메뉴_리뷰;

    }

    public static List<MenuReview> creatMenuReviewsNoContent() {

       return List.of();

    }


}
