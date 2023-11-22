package dev.yogizogi.domain.review.model.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import dev.yogizogi.global.common.status.RecommendationStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@JsonInclude(Include.NON_NULL)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MenuReviewVO {

    @Schema(description = "메뉴 리뷰 식별자")
    private Long id;

    @Schema(description = "메뉴 리뷰 내용")
    private String content;

    @Schema(description = "추천")
    private RecommendationStatus recommendationStatus;

    @Schema(description = "조회한 메뉴 리뷰 사진")
    private List<String> images = new ArrayList<>();

    @Builder
    public MenuReviewVO(Long id, String content, RecommendationStatus recommendationStatus, List<String> images) {
        this.id = id;
        this.content = content;
        this.recommendationStatus = recommendationStatus;
        this.images = images;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MenuReviewVO that)) {
            return false;
        }
        return content.equals(that.content) && recommendationStatus == that.recommendationStatus && images.equals(that.images);
    }

    @Override
    public int hashCode() {
        return Objects.hash(content, recommendationStatus, images);
    }

}
