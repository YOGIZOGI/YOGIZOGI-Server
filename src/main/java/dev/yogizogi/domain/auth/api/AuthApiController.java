package dev.yogizogi.domain.auth.api;

import dev.yogizogi.domain.auth.model.dto.request.LoginInDto;
import dev.yogizogi.domain.auth.model.dto.response.LoginOutDto;
import dev.yogizogi.domain.auth.model.dto.response.ReissueAccessTokenOutDto;
import dev.yogizogi.domain.auth.model.dto.response.VerifyCodeOutDto;
import dev.yogizogi.domain.auth.service.AuthService;
import dev.yogizogi.domain.member.model.dto.request.CreateMemberInDto;
import dev.yogizogi.domain.member.model.dto.response.CreateMemberOutDto;
import dev.yogizogi.domain.member.service.MemberService;
import dev.yogizogi.domain.security.service.JwtService;
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
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import org.springframework.http.ResponseEntity;
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

    private final MemberService memberService;
    private final AuthService authService;
    private final JwtService jwtService;

    @Operation(summary = "인증번호 요청")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "요청 완료",
                    content = @Content(schema = @Schema(implementation = SingleMessageSentResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "중복된 핸드폰 번호"
            )
    })
    @Parameter(name = "phoneNumber", description = "인증하고 싶은 핸드폰 번호", example = "01032527971")
    @GetMapping("/send-verification-code")
    public ResponseEntity sendVerificationCode(
            @RequestParam @Pattern(regexp = "^010\\d{8}$", message = "올바른 형식이 아닙니다.") String phoneNumber
    ) {

        return ResponseUtils.ok(
                Success.builder()
                        .data(authService.sendVerificationCode(phoneNumber))
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
    @GetMapping("/check-verification-code")
    public ResponseEntity checkVerificationCode(
            @RequestParam @Pattern(regexp = "^010\\d{8}$", message = "올바른 형식이 아닙니다.") String phoneNumber,
            @RequestParam @Pattern(regexp = "\\d{6}$", message = "6자리 숫자를 입력해주세요.") String code
    ) {

        return ResponseUtils.ok(
                Success.builder()
                        .data(authService.checkVerificationCode(phoneNumber, code))
                        .build());

    }

    @Operation(summary = "회원 가입")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "회원가입 완료",
                    content = @Content(schema = @Schema(implementation = CreateMemberOutDto.class))
            ),
            @ApiResponse(responseCode = "400", description = "중복된 정보(아이디, 닉네임, 핸드폰 번호)")
    })
    @PostMapping("/sign-up")
    public ResponseEntity createMember(@RequestBody @Valid CreateMemberInDto response) {

        return ResponseUtils.created(
                Success.builder()
                        .data(memberService.signUp(response))
                        .build());

    }

    @Operation(summary = "로그인")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "회원가입 완료",
                    content = @Content(schema = @Schema(implementation = LoginOutDto.class))
            ),
            @ApiResponse(responseCode = "403", description = "아이디 or 비밀번호 불일치"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 계정")
    })
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginInDto res) {
        return ResponseUtils.ok(
                Success.builder()
                        .data(authService.login(res))
                        .build());
    }

    @Operation(summary = "Access Token 재발급")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "재발급 왼료",
                    content = @Content(schema = @Schema(implementation = ReissueAccessTokenOutDto.class))
            ),
            @ApiResponse(responseCode = "403", description = "만료된 리프레쉬 토큰"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 계정")
    })
    @Parameters({
            @Parameter(name = "id", description = "식별자"),
            @Parameter(name = "accountName", description = "계정 이름"),

    })
    @GetMapping("/reissue-access-token")
    public ResponseEntity reissueAccessToken(
            @RequestParam UUID id, @RequestParam String accountName
    ) {
        return ResponseUtils.ok(
                Success.builder()
                        .data(jwtService.reissueAccessToken(id, accountName))
                        .build());
    }


}
