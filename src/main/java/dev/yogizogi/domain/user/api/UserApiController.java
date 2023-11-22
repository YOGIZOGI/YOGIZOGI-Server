package dev.yogizogi.domain.user.api;

import dev.yogizogi.domain.security.service.JwtService;
import dev.yogizogi.domain.user.model.dto.request.CreateUserProfileInDto;
import dev.yogizogi.domain.user.model.dto.response.CreateUserProfileOutDto;
import dev.yogizogi.domain.user.model.dto.response.DeleteUserOutDto;
import dev.yogizogi.domain.user.model.dto.response.FindPasswordOutDto;
import dev.yogizogi.domain.user.service.UserService;
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
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "유저 관련 API")
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserApiController {

    private final UserService userService;
    private final JwtService jwtService;

    @Operation(summary = "회원 탈퇴")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "요청 완료",
                    content = @Content(schema = @Schema(implementation = DeleteUserOutDto.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "존재하지 않는 유저"
            )
    })
    @Parameter(name = "phoneNumber", description = "삭제할 계정")
    @PutMapping("/delete")
    public ResponseEntity deleteUser(
            @RequestParam @Pattern(regexp = "^010\\d{8}$", message = "올바른 형식이 아닙니다.") String phoneNumber
    ) {
        return ResponseUtils.ok(
                Success.builder()
                        .data(userService.deleteUser(phoneNumber))
                        .build()
        );
    }

    @Operation(summary = "비밀번호 찾기")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "요청 완료",
                    content = @Content(schema = @Schema(implementation = FindPasswordOutDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "인증번호 전송 실패"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "존재하지 않는 계정"
            )
    })
    @Parameter(name = "phoneNumber", description = "찾고 싶은 계정", example = "01012345678")
    @GetMapping("/find-password")
    public ResponseEntity findPassword(
            @RequestParam @Pattern(regexp = "^010\\d{8}$", message = "올바른 형식이 아닙니다.") String phoneNumber
    ) {

        return ResponseUtils.ok(
                Success.builder()
                        .data(userService.findPassword(phoneNumber))
                        .build());

    }

    @Operation(summary = "비밀번호 수정(비로그인 상태)")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "요청 완료"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "이미 사용중인 비밀번호"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "존재하지 않는 유저"
            )
    })
    @Parameters({
            @Parameter(name = "phoneNumber", description = "변경하고 싶은 계정", example = "01012345678"),
            @Parameter(name = "password", description = "변경하고 싶은 비밀번호", example = "yogi5678!")
    })
    @PutMapping("/update-password")
    public ResponseEntity updatePassword(
            @RequestParam @Pattern(regexp = "^010\\d{8}$", message = "올바른 형식이 아닙니다.") String phoneNumber,
            @RequestParam String password) {

        return ResponseUtils.ok(
                Success.builder()
                        .data(userService.updatePassword(phoneNumber, password))
                        .build());

    }

    @Operation(summary = "프로필 설정")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "프로필 설정 완료",
                    content = @Content(schema = @Schema(implementation = CreateUserProfileOutDto.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "존재하지 않는 유저"
            )
    })
    @PutMapping("/create-profile")
    public ResponseEntity createProfile(@RequestBody @Valid CreateUserProfileInDto response) {

        return ResponseUtils.ok(
                Success.builder()
                        .data(userService.createProfile(
                                jwtService.getUserId(), response.getNickname(), response.getImageUrl(), response.getIntroduction())
                        )
                        .build());

    }


}
