package dev.yogizogi.domain.user.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@Schema(name = "프로필 생성 응답 Dto")
public class CreateUserProfileOutDto {

    @Schema(description = "등록한 닉네임", example = "요기조기")
    private String nickname;

    @Schema(description = "등록한 프로필 사진 주소", example = "")
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
