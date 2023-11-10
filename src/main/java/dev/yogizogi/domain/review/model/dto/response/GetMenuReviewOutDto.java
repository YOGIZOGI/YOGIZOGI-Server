package dev.yogizogi.domain.review.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class GetMenuReviewOutDto {

    @Schema(description = "조회한 메뉴 리뷰 식별자")
    private Long menuReviewId;

    @Schema(description = "메뉴 리뷰 내용")
    private String content;

    public static GetMenuReviewOutDto of( Long menuReviewId, String content) {
        return GetMenuReviewOutDto.builder()
                .menuReviewId(menuReviewId)
                .content(content)
                .build();
    }

}
