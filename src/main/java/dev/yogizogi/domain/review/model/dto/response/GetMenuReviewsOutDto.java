package dev.yogizogi.domain.review.model.dto.response;

import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class GetMenuReviewsOutDto {

    private Long menuId;

    private Long menuReviewId;

    private List<String> imageUrl;

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
