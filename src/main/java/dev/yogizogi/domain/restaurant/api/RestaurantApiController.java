package dev.yogizogi.domain.restaurant.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import dev.yogizogi.domain.restaurant.model.dto.request.CreateRestaurantInDto;
import dev.yogizogi.domain.restaurant.model.dto.response.CreateRestaurantOutDto;
import dev.yogizogi.domain.restaurant.model.dto.response.RetrieveRestaurantOutDto;
import dev.yogizogi.domain.restaurant.service.RestaurantService;
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
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "음식점 관련 API")
@Validated
@RestController
@RequestMapping("/api/restaurants")
@RequiredArgsConstructor
public class RestaurantApiController {

    private final RestaurantService restaurantService;

    @Operation(summary = "음식점 생성")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "음식점 생성 완료",
                    content = @Content(schema = @Schema(implementation = CreateRestaurantOutDto.class))
            ),
            @ApiResponse(responseCode = "400", description = "잘못된 정보"),
    })
    @PostMapping("/create")
    public ResponseEntity createRestaurant(@RequestBody @Valid CreateRestaurantInDto req) throws JsonProcessingException {

        return ResponseUtils.created(
                Success.builder()
                        .data(restaurantService
                                .createRestaurant(req.getName(), req.getTel(), req.getAddress(), req.getImageUrl(), req.getTypes())
                        )
                        .build());

    }

    @Operation(summary = "특정 음식점 조회")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "특정 음식점 조회 완료",
                    content = @Content(schema = @Schema(implementation = RetrieveRestaurantOutDto.class))
            ),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 음식점")
    })
    @Parameter(name = "restaurantId", description = "조회하고 싶은 음식점 식별자")
    @GetMapping("/{restaurantId}")
    public ResponseEntity retrieveRestaurant(@PathVariable String restaurantId) {

        return ResponseUtils.ok(
                Success.builder()
                        .data(restaurantService.retrieveRestaurant(UUID.fromString(restaurantId)))
                        .build());

    }


    @Operation(summary = "요기무드 별 음식점 조회")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "특정 음식점 조회 완료",
                    content = @Content(schema = @Schema(implementation = RetrieveRestaurantOutDto.class))
            ),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 음식점")
    })
    @Parameter(name = "yogiMoods", description = "선택한 요기무드", example = "[\"WITH_LOVER\", \"PAIRING_MEAL\"]")
    @GetMapping("")
    public ResponseEntity retrieveRestaurantByYogiMoods(@RequestParam List<String> yogiMoods) {

        return ResponseUtils.ok(
                Success.builder()
                        .data(restaurantService.retrieveRestaurantsByYogiMood(yogiMoods))
                        .build());

    }

}
