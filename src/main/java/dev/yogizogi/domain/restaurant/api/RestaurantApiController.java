package dev.yogizogi.domain.restaurant.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import dev.yogizogi.domain.restaurant.model.dto.request.CreateRestaurantInDto;
import dev.yogizogi.domain.restaurant.model.dto.response.CreateRestaurantOutDto;
import dev.yogizogi.domain.restaurant.service.RestaurantService;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "식당 관련 API")
@RestController
@RequestMapping("/api/restaurants")
@RequiredArgsConstructor
public class RestaurantApiController {

    private final RestaurantService restaurantService;

    @Operation(summary = "식당 생성")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "음식점 생성 완료",
                    content = @Content(schema = @Schema(implementation = CreateRestaurantOutDto.class))
            )
    })
    @PostMapping("/create")
    public ResponseEntity createRestaurant(@RequestBody @Valid CreateRestaurantInDto req) throws JsonProcessingException {

        return ResponseUtils.created(
                Success.builder()
                        .data(restaurantService
                                .createRestaurant(
                                        req.getName(),
                                        req.getAddress(),
                                        req.getTel(),
                                        req.getImageUrl()
                                ))
                        .build());

    }

//    @Operation(summary = "특정 식당 조회")
//    @ApiResponses({
//            @ApiResponse(
//                    responseCode = "201",
//                    description = "음식점 생성 완료",
//                    content = @Content(schema = @Schema(implementation = CreateRestaurantOutDto.class))
//            )
//    })
//    @GetMapping("/{restaurantId}")
//    public ResponseEntity getRestaurant(@PathVariable Long restaurantId) throws JsonProcessingException {
//
//        return ResponseUtils.created(
//                Success.builder()
//                        .data(restaurantService
//                                .getRestaurant(
//                                        req.getName(),
//                                        req.getAddress(),
//                                        req.getTel(),
//                                        req.getImageUrl()
//                                ))
//                        .build());
//
//    }

}
