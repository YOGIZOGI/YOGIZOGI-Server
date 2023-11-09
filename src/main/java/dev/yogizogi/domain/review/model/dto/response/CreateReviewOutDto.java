package dev.yogizogi.domain.review.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@Schema(name = "리뷰 생성 응답 Dto")
public class CreateReviewOutDto {

    @Schema(description = "생성한 리뷰 식별자")
    private UUID reviewId;
    public static CreateReviewOutDto of(UUID reviewId) {
        return CreateReviewOutDto.builder()
                .reviewId(reviewId)
                .build();
    }

}
