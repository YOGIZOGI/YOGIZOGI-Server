package dev.yogizogi.domain.review.model.dto.request;

import dev.yogizogi.domain.menu.model.entity.Menu;
import dev.yogizogi.domain.review.model.entity.MenuReview;
import dev.yogizogi.domain.review.model.entity.Review;
import dev.yogizogi.global.common.status.RecommendationStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(name = "메뉴 리뷰 생성 요청 Dto")
public class CreateMenuReviewInDto {

    @Schema(description = "리뷰 식별자", example = "신선해요!!")
    private UUID reviewId;

    @Schema(description = "리뷰를 작성할 메뉴 식별자", example = "2")
    private Long menuId;

    @Schema(description = "메뉴 리뷰에 작성한 내용", example = "신선해요!!")
    private String content;

    @Schema(description = "추천 여부", example = "RECOMMEND", allowableValues = {"RECOMMEND", "NOT_RECOMMEND"})
    private String recommend;

    @Schema(
            description = "메뉴 리뷰 사진",
            example = "[\"https://yogizogi-multimedia-bucket.s3.ap-northeast-2.amazonaws.com/menu-review/2/bf940c3e-cc45-4c42-9640-47fa11d73b25\","
                    + " \"https://yogizogi-multimedia-bucket.s3.ap-northeast-2.amazonaws.com/menu-review/2/1d5f7feb-2c2b-446a-8340-9f3dbd5629a9\"]")
    private List<String> imageUrl;

    @Builder
    public static MenuReview toEntity(Review review, Menu menu, String content, String recommend) {
        return MenuReview.builder()
                .review(review)
                .menu(menu)
                .content(content)
                .recommendationStatus(RecommendationStatus.valueOf(recommend))
                .build();
    }

}
