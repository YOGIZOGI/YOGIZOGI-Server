package dev.yogizogi.domain.review.service;

import static dev.yogizogi.global.common.code.ErrorCode.INVALID_YOGI_MOOD;
import static dev.yogizogi.global.common.code.ErrorCode.NOT_EXIST_REVIEW;

import dev.yogizogi.domain.review.exception.InValidYogiMoodException;
import dev.yogizogi.domain.review.exception.UnauthorizedReviewException;
import dev.yogizogi.domain.review.model.dto.request.CreateServiceReviewInDto;
import dev.yogizogi.domain.review.model.dto.response.CreateServiceReviewOutDto;
import dev.yogizogi.domain.review.model.entity.Review;
import dev.yogizogi.domain.review.model.entity.ServiceReview;
import dev.yogizogi.domain.review.model.entity.YogiMood;
import dev.yogizogi.domain.review.repository.ReviewRepository;
import dev.yogizogi.domain.review.repository.ServiceReviewRepository;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ServiceReviewService {

    private final ReviewRepository reviewRepository;
    private final ServiceReviewRepository serviceReviewRepository;

    public CreateServiceReviewOutDto createServiceReview(UUID reviewId, Double rating, List<String> yogiMoods) {

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new UnauthorizedReviewException(NOT_EXIST_REVIEW));

        List<YogiMood> yogiMoodsEnum = convertToYogiMoodEnum(yogiMoods);

        ServiceReview serviceReview = CreateServiceReviewInDto.toEntity(review, rating, yogiMoodsEnum);
        serviceReviewRepository.save(serviceReview);

        return CreateServiceReviewOutDto.of(serviceReview.getReview().getId(), serviceReview.getId());

    }

    @NotNull
    private static List<YogiMood> convertToYogiMoodEnum(List<String> yogiMoods) {

        try {
            return yogiMoods.stream()
                    .map(YogiMood::valueOf)
                    .collect(Collectors.toList());
        } catch (IllegalArgumentException e) {
            throw new InValidYogiMoodException(INVALID_YOGI_MOOD);
        }

    }

}
