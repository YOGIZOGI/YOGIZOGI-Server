package dev.yogizogi.domain.restaurant.model.dto.response;

import dev.yogizogi.domain.review.model.vo.MenuReviewVO;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@Schema(name = "특정 음식점 모든 리뷰 조회 응답 DTO")
public class RetrieveMenuReviewsByRestaurant {

    @Schema(description = "조회한 메뉴 리뷰들")
    private List<MenuReviewVO> menuReviews;

    public static RetrieveMenuReviewsByRestaurant of(List<MenuReviewVO> menuReviews) {
        return RetrieveMenuReviewsByRestaurant.builder()
                .menuReviews(menuReviews)
                .build();
    }

}
