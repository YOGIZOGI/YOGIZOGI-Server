package dev.yogizogi.domain.review.model.dto.response;

import dev.yogizogi.global.common.status.RecommendationStatus;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class GetMenuReviewsOutDto {

    private Long id;

    private List<String> imageUrl;

    private RecommendationStatus recommendationStatus;

    public static GetMenuReviewsOutDto of(Long id, List<String> imageUrl, RecommendationStatus recommendationStatus) {
        return GetMenuReviewsOutDto.builder()
                .id(id)
                .imageUrl(imageUrl)
                .recommendationStatus(recommendationStatus)
                .build();
    }

}
