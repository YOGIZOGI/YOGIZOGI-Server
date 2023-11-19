package dev.yogizogi.domain.map.api;

import dev.yogizogi.domain.map.model.dto.response.AddRestaurantOnMapInDto;
import dev.yogizogi.domain.map.service.MapService;
import dev.yogizogi.domain.security.service.JwtService;
import dev.yogizogi.global.common.model.response.Success;
import dev.yogizogi.global.util.ResponseUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "지도 관련 API")
@Validated
@RestController
@RequestMapping("/api/maps")
@RequiredArgsConstructor
public class MapApiController {

    private final MapService mapService;
    private final JwtService jwtService;

    @PostMapping("/add-restaurant")
    public ResponseEntity addRestaurantOnMap(@RequestBody AddRestaurantOnMapInDto req) {

        return ResponseUtils.ok(
                Success.builder()
                        .data(mapService
                                .addRestaurantOnMap(req.getUserId(), req.getRestaurantId()))
                        .build()
        );

    }

}
