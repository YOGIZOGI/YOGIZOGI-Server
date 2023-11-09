package dev.yogizogi.domain.review.service;

import dev.yogizogi.domain.restaurant.exception.NotExistRestaurantException;
import dev.yogizogi.domain.restaurant.model.entity.Restaurant;
import dev.yogizogi.domain.restaurant.repository.RestaurantRepository;
import dev.yogizogi.domain.review.model.dto.request.CreateReviewInDto;
import dev.yogizogi.domain.review.model.dto.response.CreateReviewOutDto;
import dev.yogizogi.domain.review.model.entity.Review;
import dev.yogizogi.domain.review.repository.ReviewRepository;
import dev.yogizogi.domain.user.exception.NotExistUserException;
import dev.yogizogi.domain.user.model.entity.User;
import dev.yogizogi.domain.user.repository.UserRepository;
import dev.yogizogi.global.common.code.ErrorCode;
import dev.yogizogi.global.util.UuidUtils;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService {

    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;
    private final ReviewRepository reviewRepository;

    public CreateReviewOutDto createReview(UUID userId, Long restaurantId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotExistUserException(ErrorCode.NOT_EXIST_USER));

        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new NotExistRestaurantException(ErrorCode.NOT_EXIST_RESTAURANT));

        Review review = CreateReviewInDto.toEntity(UuidUtils.createSequentialUUID(), user, restaurant);

        reviewRepository.save(review);

        return CreateReviewOutDto.of(review.getId());

    }

}
