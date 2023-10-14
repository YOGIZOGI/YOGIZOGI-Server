package dev.yogizogi.domain.authorization.model.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(name = "로그인 요청 Dto", description = "로그인에 필요한 정보")
public class LoginInDto {

    @Pattern(regexp = "^010\\d{8}$", message = "올바른 형식이 아닙니다.")
    @Schema(description = "핸드폰번호", example = "01012345678")
    private String phoneNumber;

    @Schema(description = "비밀번호", example = "yogi1234!")
    private String password;

}
