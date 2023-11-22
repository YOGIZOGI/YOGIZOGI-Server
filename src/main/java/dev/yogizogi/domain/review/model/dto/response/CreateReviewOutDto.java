package dev.yogizogi.domain.review.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@Schema(name = "리뷰 생성 응답 DTO")
public class CreateReviewOutDto {

    @Schema(description = "생성한 리뷰 식별자")
    private UUID reviewId;

    @Schema(description = "작성할 유저 식별자")
    private UUID userId;

    @Schema(description = "작성할 음식점 식별자")
    private UUID restaurantId;


    public static CreateReviewOutDto of(UUID reviewId, UUID userId, UUID restaurantId) {
        return CreateReviewOutDto.builder()
                .reviewId(reviewId)
                .userId(userId)
                .restaurantId(restaurantId)
                .build();
    }

}
