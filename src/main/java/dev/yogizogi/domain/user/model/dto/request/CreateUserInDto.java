package dev.yogizogi.domain.user.model.dto.request;

import dev.yogizogi.domain.user.model.entity.FirstLoginStatus;
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

    @NotBlank(message="전화번호를 입력하세요.")
    @Pattern(regexp = "^010\\d{8}$", message = "올바른 형식이 아닙니다.")
    @Schema(description = "핸드폰 번호", example = "01012345678")
    private String phoneNumber;

    @NotBlank(message="비밀번호를 입력하세요.")
    @Schema(description = "비밀번호", example = "yogi1234!")
    private String password;

    public static User toEntity(UUID id, String phoneNumber, String password, FirstLoginStatus firstLoginStatus) {
        return User.builder()
                .id(id)
                .phoneNumber(phoneNumber)
                .password(password)
                .firstLoginStatus(firstLoginStatus)
                .build();
    }


}
