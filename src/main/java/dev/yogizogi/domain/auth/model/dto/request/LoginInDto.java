package dev.yogizogi.domain.auth.model.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(name = "로그인 요청 Dto", description = "로그인에 필요한 정보")
public class LoginInDto {

    @Schema(description = "계정이름", example = "yogizogi")
    private String accountName;

    @Schema(description = "비밀번호", example = "yogi1234!")
    private String password;

}
