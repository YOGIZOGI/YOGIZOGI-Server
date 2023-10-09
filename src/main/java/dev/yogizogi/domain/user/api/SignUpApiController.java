package dev.yogizogi.domain.user.api;

import dev.yogizogi.domain.user.service.SignUpService;
import dev.yogizogi.domain.user.model.dto.request.CreateUserInDto;
import dev.yogizogi.domain.user.model.dto.response.CheckDuplicationOutDto;
import dev.yogizogi.domain.user.model.dto.response.CreateUserOutDto;
import dev.yogizogi.domain.user.service.UserService;
import dev.yogizogi.global.common.model.response.Success;
import dev.yogizogi.global.common.status.DuplicationStatus;
import dev.yogizogi.global.util.ResponseUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "회원가입 관련 API")
@RestController
@RequestMapping("/api/sign-up")
@RequiredArgsConstructor
public class SignUpApiController {

    private final SignUpService signUpService;
    private final UserService userService;
    @Operation(summary = "회원 가입")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "회원가입 완료",
                    content = @Content(schema = @Schema(implementation = CreateUserOutDto.class))
            ),
            @ApiResponse(responseCode = "400", description = "중복된 정보(아이디, 닉네임, 핸드폰 번호)")
    })
    @PostMapping("/")
    public ResponseEntity createMember(@RequestBody @Valid CreateUserInDto response) {

        return ResponseUtils.created(
                Success.builder()
                        .data(signUpService.signUp(response))
                        .build());

    }


    @Operation(summary = "계정 중복 확인")
    @ApiResponse(
            responseCode = "200",
            description = "확인 완료",
            content = @Content(schema = @Schema(implementation = CheckDuplicationOutDto.class))
    )

    @Parameter(name = "accountName", description = "중복 확인할 계정")
    @GetMapping("/check-duplication-account")
    public ResponseEntity checkAccountDuplication(@RequestParam String accountName) {

        DuplicationStatus status = DuplicationStatus.NOT_EXIST;

        if (userService.isUsedAccountName(accountName)) {
            status = DuplicationStatus.EXIST;
        }

        return ResponseUtils.ok(
                Success.builder()
                        .data(CheckDuplicationOutDto.of(status, accountName))
                        .build());

    }

    @Operation(summary = "닉네임 중복 확인")
    @ApiResponse(
            responseCode = "200",
            description = "확인 완료",
            content = @Content(schema = @Schema(implementation = CheckDuplicationOutDto.class))
    )

    @Parameter(name = "nickname", description = "중복 확인할 닉네임")
    @GetMapping("/check-duplication-nickname")
    public ResponseEntity checkNicknameDuplication(@RequestParam String nickname) {

        DuplicationStatus status = DuplicationStatus.NOT_EXIST;

        if (userService.isUsedNickname(nickname)) {
            status = DuplicationStatus.EXIST;
        }

        return ResponseUtils.ok(
                Success.builder()
                        .data(CheckDuplicationOutDto.of(status, nickname))
                        .build());

    }

}
