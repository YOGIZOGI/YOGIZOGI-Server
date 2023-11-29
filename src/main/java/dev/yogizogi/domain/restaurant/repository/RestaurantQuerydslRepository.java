package dev.yogizogi.domain.restaurant.repository;

import static dev.yogizogi.domain.restaurant.model.entity.QRestaurant.restaurant;
import static dev.yogizogi.domain.review.model.entity.QReview.review;
import static dev.yogizogi.domain.review.model.entity.QServiceReview.serviceReview;
import static dev.yogizogi.domain.review.model.entity.QServiceReviewYogiMood.serviceReviewYogiMood;

import com.querydsl.jpa.impl.JPAQueryFactory;
import dev.yogizogi.domain.restaurant.model.entity.Restaurant;
import dev.yogizogi.domain.review.model.entity.YogiMood;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RestaurantQuerydslRepository {

    private final JPAQueryFactory queryFactory;

    public Optional<List<Restaurant>> findRestaurantsByYogiMoods(List<YogiMood> yogiMoods) {

        return Optional.ofNullable(
                queryFactory
                        .select(restaurant)
                        .distinct()
                        .from(serviceReviewYogiMood)
                        .join(serviceReviewYogiMood.serviceReview, serviceReview)
                        .join(serviceReview.review, review)
                        .join(review.restaurant, restaurant)
                        .where(serviceReviewYogiMood.yogiMood.in(yogiMoods))
                        .fetch()
        );

    }

}
