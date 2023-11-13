package dev.yogizogi.domain.review.api;

import dev.yogizogi.domain.review.model.dto.request.CreateServiceReviewInDto;
import dev.yogizogi.domain.review.model.dto.response.CreateServiceReviewOutDto;
import dev.yogizogi.domain.review.service.ServiceReviewService;
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
@RequestMapping("/api/reviews/service-reviews")
public class ServiceReviewApiController {

    private final ServiceReviewService serviceReviewService;

    @Operation(
            summary = "서비스 리뷰 생성",
            description = "음식점에 대한 서비스 리뷰를 생성한다."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "서비스 리뷰 생성 완료",
                    content = @Content(schema = @Schema(implementation = CreateServiceReviewOutDto.class))
            ),
            @ApiResponse(responseCode = "403", description = "권한 없는 음식점"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 정보(사용자, 음식점)")
    })
    @PostMapping("/create")
    public ResponseEntity createMenuReview(@RequestBody @Valid CreateServiceReviewInDto req) {

        return ResponseUtils.created(
                Success.builder()
                        .data(serviceReviewService
                                .createServiceReview(
                                        req.getReviewId(),
                                        req.getRating()
                                ))
                        .build()
        );

    }

}
