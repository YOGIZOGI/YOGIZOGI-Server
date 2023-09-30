package dev.yogizogi.domain.user.model.dto.request;

import dev.yogizogi.domain.user.model.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(name = "회원가입 요청 Dto")
public class CreateUserInDto {

    @NotBlank(message="아이디를 입력하세요.")
    @Schema(description = "아이디", example = "yogizogi")
    private String accountName;

    @NotBlank(message="비밀번호를 입력하세요.")
    @Schema(description = "비밀번호", example = "yogi1234!")
    private String password;

    @NotBlank(message="닉네임을 입력하세요.")
    @Schema(description = "닉네임", example = "요기조기")
    private String nickname;

    @NotBlank(message="전화번호를 입력하세요.")
    @Pattern(regexp = "^010\\d{8}$", message = "올바른 형식이 아닙니다.")
    @Schema(description = "핸드폰 번호", example = "01012345678")
    private String phoneNumber;

    public static User toEntity(
            UUID id, CreateUserInDto createUserInDto, String password){
        return User.builder()
                .id(id)
                .accountName(createUserInDto.getAccountName())
                .password(password)
                .nickname(createUserInDto.getNickname())
                .phoneNumber(createUserInDto.getPhoneNumber())
                .build();
    }


}
