package dev.yogizogi.domain.review.service;

import static dev.yogizogi.global.common.code.ErrorCode.INVALID_YOGI_MOOD;
import static dev.yogizogi.global.common.code.ErrorCode.NOT_EXIST_REVIEW;

import dev.yogizogi.domain.review.exception.InValidYogiMoodException;
import dev.yogizogi.domain.review.exception.UnauthorizedReviewException;
import dev.yogizogi.domain.review.model.dto.request.CreateServiceReviewInDto;
import dev.yogizogi.domain.review.model.dto.response.CreateServiceReviewOutDto;
import dev.yogizogi.domain.review.model.entity.Review;
import dev.yogizogi.domain.review.model.entity.ServiceReview;
import dev.yogizogi.domain.review.model.entity.ServiceReviewYogiMood;
import dev.yogizogi.domain.review.model.entity.YogiMood;
import dev.yogizogi.domain.review.repository.ReviewRepository;
import dev.yogizogi.domain.review.repository.ServiceReviewRepository;
import dev.yogizogi.domain.review.repository.ServiceReviewYogiMoodRepository;
import dev.yogizogi.domain.review.repository.YogiMoodRepository;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ServiceReviewService {

    private final ReviewRepository reviewRepository;
    private final ServiceReviewRepository serviceReviewRepository;
    private final YogiMoodRepository yogiMoodRepository;
    private final ServiceReviewYogiMoodRepository serviceReviewYogiMoodRepository;

    public CreateServiceReviewOutDto createServiceReview(UUID reviewId, Double rating, List<String> yogiMoodsString) {

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new UnauthorizedReviewException(NOT_EXIST_REVIEW));

        ServiceReview serviceReview = CreateServiceReviewInDto.toEntity(review, rating);
        serviceReview = serviceReviewRepository.save(serviceReview);

        List<ServiceReviewYogiMood> serviceReviewYogiMoods
                = createServiceReviewYogiMood(serviceReview, convertToYogiMoodEntity(yogiMoodsString));

        serviceReviewYogiMoodRepository.saveAll(serviceReviewYogiMoods);

        return CreateServiceReviewOutDto.of(serviceReview.getReview().getId(), serviceReview.getId());

    }

    /**
     * String으로 받은 YogiMood를 Entity로 변환
     */
    private List<YogiMood> convertToYogiMoodEntity(List<String> yogiMoodsString) {
        return yogiMoodsString.stream()
                .map(yogiMood ->
                        yogiMoodRepository.findByName(yogiMood)
                                .orElseThrow(() -> new InValidYogiMoodException(INVALID_YOGI_MOOD)))
                .collect(Collectors.toList());
    }

    /**
     * Service Review에 YogiMood에 대한 정보 저장
     */
    private List<ServiceReviewYogiMood> createServiceReviewYogiMood(ServiceReview serviceReview, List<YogiMood> yogiMoods) {

        return yogiMoods.stream()
                .map(yogiMood ->
                        ServiceReviewYogiMood.builder()
                                .serviceReview(serviceReview)
                                .yogiMood(yogiMood)
                                .build()
                ).collect(Collectors.toList());

    }


}
