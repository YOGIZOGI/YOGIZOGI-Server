package dev.yogizogi.domain.review.model.dto.request;

import dev.yogizogi.domain.review.model.entity.Review;
import dev.yogizogi.domain.review.model.entity.ServiceReview;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "서비스 리뷰 생성 요청 DTO")
public class CreateServiceReviewInDto {

    @Schema(description = "리뷰 식별자")
    private UUID reviewId;

    @Min(1) @Max(5)
    @Schema(description = "서비스 평점", example = "신선해요!!")
    private Double rating;

    @Schema(
            description = "요기무드",
            example = "[\"WITH_LOVER\", \"PAIRING_MEAL\"]",
            allowableValues = {
                    "SOLO", "WITH_LOVER", "WITH_FRIENDS", "WITH_PARENT", "WITH_CHILD", "WITH_COLLEAGUE",
                    "LIGHT_MEAL", "GOURMET_MEAL", "PAIRING_MEAL", "USiNESS_MEETING", "GROUP_MEETING", "ANNIVERSARY"
            }
    )
    private List<String> yogiMoods;

    @Builder
    public static ServiceReview toEntity(Review review, Double rating) {
        return ServiceReview.builder()
                .review(review)
                .rating(rating)
                .build();

    }

}
