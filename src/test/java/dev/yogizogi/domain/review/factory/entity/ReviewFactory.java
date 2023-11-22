package dev.yogizogi.domain.review.factory.entity;

import dev.yogizogi.domain.review.factory.fixtures.ReviewFixtures;
import dev.yogizogi.domain.review.model.entity.Review;
import java.util.List;

public class ReviewFactory {

    public static Review createReview() {

        return Review.builder()
                .id(ReviewFixtures.식별자)
                .user(ReviewFixtures.작성할_유저)
                .restaurant(ReviewFixtures.작성할_음식점)
                .build();

    }

    public static List<Review> createReviews() {

        return List.of(createReview());

    }

}
