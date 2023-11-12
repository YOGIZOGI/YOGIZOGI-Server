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

    @Schema(description = "조회한 메뉴 리뷰들")
    private List<GetMenuReviewOutDto> menuReviews;

    public static GetMenuReviewsOutDto of(Long menuId, List<GetMenuReviewOutDto> menuReviews) {
        return GetMenuReviewsOutDto.builder()
                .menuId(menuId)
                .menuReviews(menuReviews)
                .build();
    }

}
