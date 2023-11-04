package dev.yogizogi.domain.menu.api;

import dev.yogizogi.domain.menu.model.dto.request.CreateMenuInDto;
import dev.yogizogi.domain.menu.model.dto.response.CreateMenuOutDto;
import dev.yogizogi.domain.menu.service.MenuService;
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

@Tag(name = "메뉴 관련 API")
@Validated
@RestController
@RequestMapping("/api/menus")
@RequiredArgsConstructor
public class MenuApiController {

    private final MenuService menuService;

    @Operation(summary = "메뉴 생성")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "메뉴 생성 완료",
                    content = @Content(schema = @Schema(implementation = CreateMenuOutDto.class))
            ),
            @ApiResponse(responseCode = "400", description = "잘못된 정보"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 음식점"),
    })
    @PostMapping("/create")
    public ResponseEntity createMenu(@RequestBody @Valid CreateMenuInDto req) {

        return ResponseUtils.created(
                        Success.builder()
                                .data(menuService.createMenu(req))
                                .build()
        );

    }

}
