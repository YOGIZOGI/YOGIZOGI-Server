package dev.yogizogi.domain.review.factory.entity;

import dev.yogizogi.domain.review.factory.fixtures.ServiceReviewFixtures;
import dev.yogizogi.domain.review.model.entity.ServiceReview;

public class ServiceReviewFactory {

    public static ServiceReview creatServicesReview() {

        ServiceReview 서비스_리뷰 = ServiceReview.builder()
                .review(ReviewFactory.createReview())
                .rating(ServiceReviewFixtures.평점)
                .build();

        return 서비스_리뷰;

    }

}
