package dev.yogizogi.domain.review.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@Schema(name = "메뉴 리뷰 생성 응답 Dto")
public class CreateMenuReviewOutDto {

    @Schema(description = "생성한 메뉴 리뷰 식별자")
    private Long menuReviewId;

    @Schema(description = "메뉴 리뷰를 생성한 리뷰 식별자")
    private UUID reviewId;

    @Schema(description = "메뉴 리뷰를 생성한 메뉴 식별자")
    private Long menuId;

    public static CreateMenuReviewOutDto of(Long id, UUID reviewId, Long menuId) {
        return CreateMenuReviewOutDto.builder()
                .menuReviewId(id)
                .reviewId(reviewId)
                .menuId(menuId)
                .build();
    }


}
