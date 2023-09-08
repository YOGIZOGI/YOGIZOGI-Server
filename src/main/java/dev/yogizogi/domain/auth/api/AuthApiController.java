package dev.yogizogi.domain.auth.api;

import dev.yogizogi.domain.auth.model.dto.request.LoginInDto;
import dev.yogizogi.domain.auth.service.AuthService;
import dev.yogizogi.global.common.code.SuccessCode;
import dev.yogizogi.global.common.model.response.ApiResponse;
import dev.yogizogi.global.common.model.response.Success;
import dev.yogizogi.global.util.ResponseUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "인증 관련 API")
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthApiController {

    private final AuthService authService;

    @Operation(summary = "인증번호 요청")
    @GetMapping("/send-verification-code")
    @Parameter(name = "phoneNumber", description = "인증하고 싶은 핸드폰 번호", example = "01032527971")
    public ApiResponse sendVerificationCode(
            @RequestParam @Pattern(regexp = "^010\\d{8}$", message = "올바른 형식이 아닙니다.") String phoneNumber
    ) {

        return ResponseUtils.ok
                (Success.builder()
                        .code(SuccessCode.OK)
                        .data(authService.sendVerificationCode(phoneNumber))
                        .build());

    }

    @Operation(summary = "인증번호 확인")
    @GetMapping("/check-verification-code")
    @Parameters({
            @Parameter(name = "phoneNumber", description = "인증하고 싶은 핸드폰 번호", example = "01032527971"),
            @Parameter(name = "code", description = "인증코드", example = "123456"),
    })
    public ApiResponse checkVerificationCode(
            @RequestParam @Pattern(regexp = "^010\\d{8}$", message = "올바른 형식이 아닙니다.") String phoneNumber,
            @RequestParam @Pattern(regexp = "\\d{6}$", message = "6자리 숫자를 입력해주세요.") String code
    ) {

        return ResponseUtils.ok
                (Success.builder()
                        .code(SuccessCode.OK)
                        .data(authService.checkVerificationCode(phoneNumber, code))
                        .build());

    }

    @Operation(summary = "로그인")
    @PostMapping("/login")
    public ApiResponse login(@RequestBody LoginInDto res) {
        return ResponseUtils.ok
                (Success.builder()
                        .code(SuccessCode.OK)
                        .data(authService.login(res))
                        .build());

    }

}
