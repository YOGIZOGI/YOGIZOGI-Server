package dev.yogizogi.domain.review.service;

import static dev.yogizogi.global.common.code.ErrorCode.NOT_EXIST_REVIEW;

import dev.yogizogi.domain.review.exception.NotExistReviewException;
import dev.yogizogi.domain.review.model.dto.request.CreateServiceReviewInDto;
import dev.yogizogi.domain.review.model.dto.response.CreateServiceReviewOutDto;
import dev.yogizogi.domain.review.model.entity.Review;
import dev.yogizogi.domain.review.model.entity.ServiceReview;
import dev.yogizogi.domain.review.repository.ReviewRepository;
import dev.yogizogi.domain.review.repository.ServiceReviewRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ServiceReviewService {

    private final ReviewRepository reviewRepository;
    private final ServiceReviewRepository serviceReviewRepository;

    public CreateServiceReviewOutDto createServiceReview(UUID reviewId, Double rating) {

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new NotExistReviewException(NOT_EXIST_REVIEW));

        ServiceReview serviceReview = CreateServiceReviewInDto.toEntity(review, rating);
        serviceReviewRepository.save(serviceReview);

        return CreateServiceReviewOutDto.of(serviceReview.getReview().getId(), serviceReview.getId());

    }

}
