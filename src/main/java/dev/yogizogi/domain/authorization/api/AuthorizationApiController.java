package dev.yogizogi.domain.authorization.api;

import dev.yogizogi.domain.authorization.model.dto.request.LoginInDto;
import dev.yogizogi.domain.authorization.model.dto.response.LoginOutDto;
import dev.yogizogi.domain.authorization.model.dto.response.ReissueAccessTokenOutDto;
import dev.yogizogi.domain.authorization.service.AuthorizationService;
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
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "권한 관련 API")
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthorizationApiController {

    private final AuthorizationService authorizationService;
    private final JwtService jwtService;

    @Operation(summary = "로그인")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "로그인 완료",
                    content = @Content(schema = @Schema(implementation = LoginOutDto.class))
            ),
            @ApiResponse(responseCode = "403", description = "아이디 or 비밀번호 불일치"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 계정")
    })
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginInDto res) {
        return ResponseUtils.ok(
                Success.builder()
                        .data(authorizationService.login(res))
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
            @RequestParam UUID id, @RequestParam String accountName) {
        return ResponseUtils.ok(
                Success.builder()
                        .data(jwtService.reissueAccessToken(id, accountName))
                        .build());
    }

}
