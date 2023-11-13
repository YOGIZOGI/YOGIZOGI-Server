package dev.yogizogi.domain.review.factory.entity;

import dev.yogizogi.domain.review.factory.fixtures.MenuReviewFixtures;
import dev.yogizogi.domain.review.model.entity.MenuReviewImage;
import java.util.List;
import java.util.stream.Collectors;

public class MenuReviewImageFactory {

    public static List<MenuReviewImage> creatMenuReviewImage() {

        List<MenuReviewImage> 조회할_메뉴_리뷰_사진 = MenuReviewFixtures.이미지_목록.stream()
                .map(url -> MenuReviewImage.builder()
                        .menuReview(MenuReviewFactory.creatMenuReview())
                        .url(url)
                        .build()
                )
                .collect(Collectors.toList());


        return 조회할_메뉴_리뷰_사진;

    }

}
