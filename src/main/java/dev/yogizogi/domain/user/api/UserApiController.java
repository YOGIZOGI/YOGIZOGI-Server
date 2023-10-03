package dev.yogizogi.domain.user.api;

import dev.yogizogi.domain.user.model.dto.response.DeleteUserOutDto;
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
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "유저 관련 API")
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;

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
    @Parameter(name = "accountName", description = "삭제할 계정")
    @PutMapping("/delete")
    public ResponseEntity deleteUser(@RequestParam String accountName) {
        return ResponseUtils.ok(
                Success.builder()
                        .data(userService.deleteUser(accountName))
                        .build()
        );
    }

    @Operation(summary = "비밀번호 찾기")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "확인 완료"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "존재하지 않는 유저"
            )
    })
    @Parameter(name = "accountName", description = "찾고 싶은 계정", example = "yogizogi")
    @GetMapping("/find-password")
    public ResponseEntity changePassword(@RequestParam String accountName) {

        return ResponseUtils.ok(
                Success.builder()
                        .data(userService.findPassword(accountName))
                        .build());

    }

    @Operation(summary = "비밀번호 수정(비로그인 상태)")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "확인 완료"
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
            @Parameter(name = "accountName", description = "변경하고 싶은 계정", example = "yogizogi"),
            @Parameter(name = "password", description = "변경하고 싶은 비밀번호", example = "yogi5678!")
    })
    @PutMapping("/update-password")
    public ResponseEntity updatePassword(
            @RequestParam String accountName,
            @RequestParam String password
    ) {

        return ResponseUtils.ok(
                Success.builder()
                        .data(userService.updatePassword(accountName, password))
                        .build());

    }


}
