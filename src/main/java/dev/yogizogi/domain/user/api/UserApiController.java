package dev.yogizogi.domain.user.api;

import dev.yogizogi.domain.auth.model.dto.response.SendVerificationCodeOutDto;
import dev.yogizogi.domain.user.model.dto.response.DeleteUserOutDto;
import dev.yogizogi.domain.user.service.UserService;
import dev.yogizogi.global.common.model.response.Success;
import dev.yogizogi.global.util.ResponseUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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


}
