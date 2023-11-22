package dev.yogizogi.domain.review.model.dto.request;

import dev.yogizogi.domain.restaurant.model.entity.Restaurant;
import dev.yogizogi.domain.review.model.entity.Review;
import dev.yogizogi.domain.user.model.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(name = "리뷰 생성 요청 DTO")
public class CreateReviewInDto {

    @Schema(description = "리뷰를 작성할 유저 식별자")
    private UUID userId;

    @Schema(description = "리뷰를 작성할 음식점 식별자", example = "1")
    private UUID restaurantId;

    @Builder
    public static Review toEntity(UUID id, User user, Restaurant restaurant) {
        return Review.builder()
                .id(id)
                .user(user)
                .restaurant(restaurant)
                .build();
    }

}
