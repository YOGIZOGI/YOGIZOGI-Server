package dev.yogizogi.domain.user.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@Schema(name = "프로필 생성 응답 DTO")
public class CreateUserProfileOutDto {

    @Schema(description = "등록한 닉네임", example = "요기조기")
    private String nickname;

    @Schema(description = "등록한 프로필 사진 주소", example = "https://yogizogi-multimedia-bucket.s3.ap-northeast-2.amazonaws.com/profile/11ee69ab-2d85-5237-84bb-cf5743b7e933/4a31c423-68c7-422e-bdcb-29928494f6d8")
    private String imageUrl;

    @Schema(description = "등록한 소개", example = "햄버거좋아용")
    private String introduction;

    public static CreateUserProfileOutDto of(String nickname, String imageUrl, String introduction) {
        return CreateUserProfileOutDto.builder()
                .nickname(nickname)
                .imageUrl(imageUrl)
                .introduction(introduction)
                .build();
    }

}
