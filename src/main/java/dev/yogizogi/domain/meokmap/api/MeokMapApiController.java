package dev.yogizogi.domain.meokmap.api;

import dev.yogizogi.domain.meokmap.model.dto.request.AddRestaurantOnMeokMapInDto;
import dev.yogizogi.domain.meokmap.model.dto.request.RemoveRestaurantOnMeokMapInDto;
import dev.yogizogi.domain.meokmap.model.dto.response.AddRestaurantOnMeokMapOutDto;
import dev.yogizogi.domain.meokmap.model.dto.response.GetMeokMapOutDto;
import dev.yogizogi.domain.meokmap.service.MeokMapService;
import dev.yogizogi.domain.security.service.JwtService;
import dev.yogizogi.global.common.model.response.Success;
import dev.yogizogi.global.util.ResponseUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "지도 관련 API")
@Validated
@RestController
@RequestMapping("/api/meok-maps")
@RequiredArgsConstructor
public class MeokMapApiController {

    private final MeokMapService meokMapService;
    private final JwtService jwtService;

    @Operation(
            summary = "먹지도 조회",
            description = "먹지도에 등록한 모든 음식점을 조회"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "먹지도 조회 완료",
                    content = @Content(schema = @Schema(implementation = GetMeokMapOutDto.class))
            ),
            @ApiResponse(responseCode = "404", description = "존재하지 않은 정보(유저)")
    })
    @GetMapping("")
    public ResponseEntity getMeokMap() {

        return ResponseUtils.ok(
                Success.builder()
                        .data(meokMapService.getMeokMap(jwtService.getUserId()))
                        .build()
        );

    }

    @Operation(
            summary = "먹지도 음식점 추가",
            description = "먹지도에 하나의 음식점을 추가"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "먹지도 추가 완료",
                    content = @Content(schema = @Schema(implementation = AddRestaurantOnMeokMapOutDto.class))
            ),
            @ApiResponse(responseCode = "404", description = "존재하지 않은 정보(유저, 음식점)")
    })
    @PostMapping("/add/restaurants")
    public ResponseEntity addRestaurantOnMap(@RequestBody AddRestaurantOnMeokMapInDto req) {

        return ResponseUtils.ok(
                Success.builder()
                        .data(meokMapService
                                .addRestaurantOnMeokMap(req.getUserId(), req.getRestaurantId()))
                        .build()
        );

    }

    @Operation(
            summary = "먹지도 음식점 삭제",
            description = "먹지도에 등록되어 있는 음식점 삭제"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "먹지도에서 음식점 삭제 완료",
                    content = @Content(schema = @Schema(implementation = String.class))
            ),
            @ApiResponse(responseCode = "400", description = "먹지도에서 삭제 실패"),
            @ApiResponse(responseCode = "404", description = "존재하지 않은 정보(유저, 음식점)")
    })
    @DeleteMapping("/remove/restaurants")
    public ResponseEntity removeRestaurantFromMap(@RequestBody RemoveRestaurantOnMeokMapInDto req) {

        return ResponseUtils.ok(
                Success.builder()
                        .data(meokMapService
                                .removeRestaurantFromMeokMap(req.getUserId(), req.getRestaurantId()))
                        .build()
        );

    }

}
