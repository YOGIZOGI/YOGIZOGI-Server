package dev.yogizogi.domain.meokprofile.api;

import dev.yogizogi.domain.meokprofile.model.dto.request.CreateMeokProfileInDto;
import dev.yogizogi.domain.meokprofile.model.dto.response.CreateMeokProfileOutDto;
import dev.yogizogi.domain.meokprofile.service.MeokProfileService;
import dev.yogizogi.domain.security.service.JwtService;
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

@Tag(name = "먹프로필 관련 API")
@Validated
@RestController
@RequestMapping("/api/meok-profile")
@RequiredArgsConstructor
public class MeokProfileApiController {

    private final MeokProfileService meokProfileService;
    private final JwtService jwtService;

    @Operation(summary = "먹프로필 생성")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "먹프로필 생성 완료",
                    content = @Content(schema = @Schema(implementation = CreateMeokProfileOutDto.class))
            ),
            @ApiResponse(responseCode = "400", description = "잘못된 정보"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 유저")
    })
    @PostMapping("/create")
    public ResponseEntity createMember(@RequestBody @Valid CreateMeokProfileInDto response) {

        return ResponseUtils.created(
                Success.builder()
                        .data(meokProfileService.createMeokProfile(
                                jwtService.getUserId(),
                                response.getSpicyPreference(),
                                response.getSaltyPreference(),
                                response.getSweetnessPreference()
                        ))
                        .build());

    }

}
