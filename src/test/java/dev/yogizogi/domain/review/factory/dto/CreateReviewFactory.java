package dev.yogizogi.domain.review.factory.dto;

import dev.yogizogi.domain.review.factory.fixtures.ReviewFixtures;
import dev.yogizogi.domain.review.model.dto.request.CreateReviewInDto;
import dev.yogizogi.domain.review.model.dto.response.CreateReviewOutDto;

public class CreateReviewFactory {

    public static CreateReviewInDto createReviewInDto() {
        return new CreateReviewInDto(ReviewFixtures.작성할_유저.getId(), ReviewFixtures.작성할_음식점.getId());
    }

    public static CreateReviewOutDto createReviewOutDto() {
        return CreateReviewOutDto.of(ReviewFixtures.식별자, ReviewFixtures.작성할_유저.getId(), ReviewFixtures.작성할_음식점.getId());
    }
}
