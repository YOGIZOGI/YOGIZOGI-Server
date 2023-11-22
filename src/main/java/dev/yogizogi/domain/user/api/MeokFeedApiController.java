package dev.yogizogi.domain.user.api;

import dev.yogizogi.domain.user.model.dto.response.RetrieveMeokFeedOutDto;
import dev.yogizogi.domain.user.service.MeokFeedService;
import dev.yogizogi.global.common.model.response.Success;
import dev.yogizogi.global.util.ResponseUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class MeokFeedApiController {

    private final MeokFeedService meokFeedService;

    @Operation(
            summary = "먹피드 조회",
            description = "자신이 작성한 리뷰를 볼 수 있는 먹피드 조회"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "먹피드 조회 완료",
                    content = @Content(schema = @Schema(implementation = RetrieveMeokFeedOutDto.class))
            ),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 정보(사용자)")
    })
    @GetMapping("/{userId}/meok-feed")
    public ResponseEntity retrieveMeokFeed(@PathVariable String userId) {
        return ResponseUtils.ok(
                Success.builder()
                        .data(meokFeedService
                                .retrieveMeokFeed(UUID.fromString(userId)))
                        .build());
    }

}
