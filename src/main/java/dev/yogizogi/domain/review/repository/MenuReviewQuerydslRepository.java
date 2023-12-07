package dev.yogizogi.domain.review.repository;

import static dev.yogizogi.domain.meokprofile.model.entity.QMeokProfile.meokProfile;
import static dev.yogizogi.domain.restaurant.model.entity.QRestaurant.restaurant;
import static dev.yogizogi.domain.review.model.entity.QMenuReview.menuReview;
import static dev.yogizogi.domain.review.model.entity.QReview.review;
import static dev.yogizogi.domain.user.model.entity.QUser.user;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import dev.yogizogi.domain.restaurant.model.entity.Restaurant;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MenuReviewQuerydslRepository {

    private final JPAQueryFactory queryFactory;

    public Optional<List<Tuple>> retrieveMenuReviewsByRestaurant(Restaurant r) {

        return Optional.ofNullable(
                queryFactory
                        .select(menuReview, user.profile, meokProfile)
                        .from(menuReview)
                        .join(menuReview.review, review)
                        .join(review.restaurant, restaurant)
                        .join(review.user, user)
                        .leftJoin(user.meokProfile, meokProfile)
                        .where(restaurant.eq(r))
                        .fetch()
        );


    }

}
