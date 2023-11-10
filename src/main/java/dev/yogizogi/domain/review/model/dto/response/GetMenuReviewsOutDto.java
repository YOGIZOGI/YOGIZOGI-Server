package dev.yogizogi.domain.review.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@Schema(name = "특정 메뉴 모든 리뷰 조회 응답 Dto")
public class GetMenuReviewsOutDto {

    @Schema(description = "조회한 메뉴 식별자")
    private Long menuId;

    @Schema(description = "조회한 메뉴 리뷰 식별자")
    private Long menuReviewId;

    @Schema(description = "조회한 메뉴 리뷰 사진")
    private List<String> imageUrl;

    @Schema(description = "추천")
    private String recommendationStatus;

    public static GetMenuReviewsOutDto of(Long menuId, Long menuReviewId, List<String> imageUrl, String recommendationStatus) {
        return GetMenuReviewsOutDto.builder()
                .menuId(menuId)
                .menuReviewId(menuReviewId)
                .imageUrl(imageUrl)
                .recommendationStatus(recommendationStatus)
                .build();
    }

}
