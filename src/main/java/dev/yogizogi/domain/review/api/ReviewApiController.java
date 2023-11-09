package dev.yogizogi.domain.review.api;

import dev.yogizogi.domain.review.model.dto.request.CreateReviewInDto;
import dev.yogizogi.domain.review.model.dto.response.CreateReviewOutDto;
import dev.yogizogi.domain.review.service.ReviewService;
import dev.yogizogi.global.common.model.response.Success;
import dev.yogizogi.global.util.ResponseUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "리뷰 관련 API")
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reviews")
public class ReviewApiController {

    private final ReviewService reviewService;

    @Operation(summary = "리뷰 생성")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "리뷰 생성 완료",
                    content = @Content(schema = @Schema(implementation = CreateReviewOutDto.class))
            ),
            @ApiResponse(responseCode = "404", description = "존재하지 않은 정보(유저, 음식점)")
    })
    @PostMapping("/create")
    public ResponseEntity createReview(@RequestBody @Valid CreateReviewInDto req) {

        return ResponseUtils.created(
                Success.builder()
                        .data(reviewService.createReview(req.getUserId(), req.getRestaurantId()))
                        .build()
        );

    }


}
