package dev.yogizogi.domain.authorization.api;

import dev.yogizogi.domain.authorization.model.dto.response.SendVerificationCodeOutDto;
import dev.yogizogi.domain.authorization.model.dto.response.VerifyCodeOutDto;
import dev.yogizogi.domain.authorization.service.VerificationService;
import dev.yogizogi.global.common.model.response.Success;
import dev.yogizogi.global.util.ResponseUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "인증 관련 API")
@Validated
@RestController
@RequestMapping("/api/verification")
@RequiredArgsConstructor
public class VerificationApiController {

    private final VerificationService verificationService;

    @Operation(summary = "인증번호 요청")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "요청 완료",
                    content = @Content(schema = @Schema(implementation = SendVerificationCodeOutDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "이미 존재하는 핸드폰 번호"
            )
    })
    @Parameter(name = "phoneNumber", description = "인증하고 싶은 핸드폰 번호", example = "01032527971")
    @GetMapping("/code")
    public ResponseEntity sendVerificationCode(
            @RequestParam @Pattern(regexp = "^010\\d{8}$", message = "올바른 형식이 아닙니다.") String phoneNumber
    ) {

        return ResponseUtils.ok(
                Success.builder()
                        .data(verificationService.sendVerificationCodeForSignUp(phoneNumber))
                        .build());

    }

    @Operation(summary = "인증번호 확인")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "확인 완료",
                    content = @Content(schema = @Schema(implementation = VerifyCodeOutDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "올바른 형식이 아닌 핸드폰 번호 또는 인증 번호"
            )
    })
    @Parameters({
            @Parameter(name = "phoneNumber", description = "인증하고 싶은 핸드폰 번호", example = "01032527971"),
            @Parameter(name = "code", description = "인증코드(6자리)", example = "123456"),
    })
    @GetMapping("/check")
    public ResponseEntity checkVerificationCode(
            @RequestParam @Pattern(regexp = "^010\\d{8}$", message = "올바른 형식이 아닙니다.") String phoneNumber,
            @RequestParam @Pattern(regexp = "\\d{6}$", message = "6자리 숫자를 입력해주세요.") String code
    ) {

        return ResponseUtils.ok(
                Success.builder()
                        .data(verificationService.checkVerificationCode(phoneNumber, code))
                        .build());

    }

}
