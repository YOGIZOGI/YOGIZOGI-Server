package dev.yogizogi.domain.user.model.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(name = "프로필 생성 요청 Dto")
public class CreateUserProfileInDto {

    @NotBlank
    @Schema(description = "닉네임", example = "요기조기")
    private String nickname;

    @Schema(description = "프로필 사진 주소", example = "")
    private String imageUrl;

    @Schema(description = "소개", example = "햄버거좋아용")
    private String introduction;

}
