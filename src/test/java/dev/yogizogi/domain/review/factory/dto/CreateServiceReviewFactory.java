package dev.yogizogi.domain.review.factory.dto;

import dev.yogizogi.domain.review.factory.fixtures.ReviewFixtures;
import dev.yogizogi.domain.review.factory.fixtures.ServiceReviewFixtures;
import dev.yogizogi.domain.review.model.dto.request.CreateServiceReviewInDto;
import dev.yogizogi.domain.review.model.dto.response.CreateServiceReviewOutDto;

public class CreateServiceReviewFactory {

    public static CreateServiceReviewInDto createServiceReviewInDto() {
        return new CreateServiceReviewInDto(
                ReviewFixtures.식별자,
                ServiceReviewFixtures.평점
        );
    }

    public static CreateServiceReviewOutDto createServiceReviewOutDto() {
        return CreateServiceReviewOutDto.of(
                ReviewFixtures.식별자,
                ServiceReviewFixtures.서비스_리뷰_식별자
        );
    }

}
