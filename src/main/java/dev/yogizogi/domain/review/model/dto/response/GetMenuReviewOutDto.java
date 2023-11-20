package dev.yogizogi.domain.review.model.dto.response;

import dev.yogizogi.domain.meokprofile.model.vo.MeokProfileVO;
import dev.yogizogi.domain.review.model.vo.MenuReviewVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@Schema(name = "특정 메뉴 단일 리뷰 응답 Dto")
public class GetMenuReviewOutDto {

    @Schema(description = "조회한 메뉴 리뷰 식별자")
    private Long menuReviewId;

    @Schema(description = "사용자 닉네임")
    private String nickname;

    @Schema(description = "사용자 먹프로필 정보")
    private MeokProfileVO meokProfile;

    @Schema(description = "메뉴 리뷰 정보")
    private MenuReviewVO menuReview;

    public static GetMenuReviewOutDto of(Long menuReviewId, String nickname, MeokProfileVO meokProfile, MenuReviewVO menuReview) {
        return GetMenuReviewOutDto.builder()
                .menuReviewId(menuReviewId)
                .nickname(nickname)
                .meokProfile(meokProfile)
                .menuReview(menuReview)
                .build();
    }

}
