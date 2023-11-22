package dev.yogizogi.domain.user.model.dto.response;

import dev.yogizogi.domain.review.model.vo.MenuReviewVO;
import dev.yogizogi.domain.user.model.entity.Profile;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@Schema(name = "먹피드 조회 응답 DTO")
public class RetrieveMeokFeedOutDto {

    @Schema(description = "작성한 유저 프로필")
    private Profile userProfile;

    @Schema(description = "작성한 메뉴 리뷰들")
    private List<MenuReviewVO> menuReviews;

    public static RetrieveMeokFeedOutDto of(Profile userProfile, List<MenuReviewVO> menuReviews) {
        return RetrieveMeokFeedOutDto.builder()
                .userProfile(userProfile)
                .menuReviews(menuReviews)
                .build();
    }

}
