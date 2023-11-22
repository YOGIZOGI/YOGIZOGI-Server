package dev.yogizogi.domain.review.model.dto.request;

import dev.yogizogi.domain.review.model.entity.Review;
import dev.yogizogi.domain.review.model.entity.ServiceReview;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(name = "서비스 리뷰 생성 요청 DTO")
public class CreateServiceReviewInDto {

    @Schema(description = "리뷰 식별자")
    private UUID reviewId;

    @Min(1) @Max(5)
    @Schema(description = "서비스 평점", example = "신선해요!!")
    private Double rating;

    @Builder
    public static ServiceReview toEntity(Review review, Double rating) {
        return ServiceReview.builder()
                .review(review)
                .rating(rating)
                .build();

    }

}
