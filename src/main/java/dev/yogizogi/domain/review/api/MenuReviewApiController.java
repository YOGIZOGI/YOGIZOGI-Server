package dev.yogizogi.domain.review.api;

import dev.yogizogi.domain.review.model.dto.request.CreateMenuReviewInDto;
import dev.yogizogi.domain.review.model.dto.response.CreateMenuReviewOutDto;
import dev.yogizogi.domain.review.model.dto.response.GetMenuReviewOutDto;
import dev.yogizogi.domain.review.model.dto.response.GetMenuReviewsOutDto;
import dev.yogizogi.domain.review.service.MenuReviewService;
import dev.yogizogi.global.common.model.response.Success;
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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "리뷰 관련 API")
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reviews/menu-reviews")
public class MenuReviewApiController {

    private final MenuReviewService menuReviewService;

    @Operation(
            summary = "메뉴 리뷰 생성",
            description = "메뉴에 대한 리뷰를 생성한다."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "메뉴 리뷰 생성 완료",
                    content = @Content(schema = @Schema(implementation = CreateMenuReviewOutDto.class))
            ),
            @ApiResponse(responseCode = "403", description = "권한 없는 음식점"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 정보(사용자, 음식점)")
    })
    @PostMapping("/create")
    public ResponseEntity createMenuReview(@RequestBody @Valid CreateMenuReviewInDto req) {

        return ResponseUtils.created(
                Success.builder()
                        .data(menuReviewService
                                .createMenuReview(
                                        req.getReviewId(),
                                        req.getMenuId(),
                                        req.getContent(),
                                        req.getRecommend(),
                                        req.getImageUrl()
                                ))
                        .build()
        );

    }

    @Operation(
            summary = "메뉴 모든 리뷰 조회",
            description = "메뉴에 대한 모든 리뷰를 불러온다."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "메뉴 리뷰 조회 완료",
                    content = @Content(schema = @Schema(implementation = GetMenuReviewsOutDto.class))
            ),
            @ApiResponse(responseCode = "403", description = "권한 없는 음식점"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 정보(메뉴, 리뷰)")
    })
    @Parameter(name = "menuId", description = "조회할 메뉴의 식별자")
    @GetMapping("/menus/{menuId}")
    public ResponseEntity getMenuReviews(@PathVariable Long menuId) {

        GetMenuReviewsOutDto res = menuReviewService.getMenuReviews(menuId);

        if (res.getMenuReviews().size() == 0) {
            res = null;
        }

        return ResponseUtils.ok(
                Success.builder()
                        .data(res)
                        .build()
        );

    }

    @Operation(
            summary = "메뉴 단일 리뷰 조회",
            description = "메뉴에 대한 하나의 메뉴 리뷰를 조회"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "메뉴 단일 리뷰 조회 완료",
                    content = @Content(schema = @Schema(implementation = GetMenuReviewOutDto.class))
            ),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 정보(메뉴 리뷰)")
    })
    @Parameter(name = "menuReviewId", description = "조회할 메뉴 리뷰의 식별자")
    @GetMapping("/{menuReviewId}")
    public ResponseEntity getMenuReview(@PathVariable Long menuReviewId) {

        GetMenuReviewOutDto res = menuReviewService.getMenuReview(menuReviewId);

        return ResponseUtils.ok(
                Success.builder()
                        .data(res)
                        .build()
        );

    }


}
