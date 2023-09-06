package dev.yogizogi.domain.user.api;

import dev.yogizogi.domain.user.model.dto.request.CreateMemberInDto;
import dev.yogizogi.domain.user.model.dto.response.CreateMemberOutDto;
import dev.yogizogi.domain.user.service.MemberService;
import dev.yogizogi.global.common.code.SuccessCode;
import dev.yogizogi.global.common.model.response.ApiResponse;
import dev.yogizogi.global.common.model.response.Success;
import dev.yogizogi.global.util.ResponseUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "회원 관련 API")
@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    @Operation(summary = "회원 가입")
    @PostMapping("/sign-up")
    public ApiResponse createMember(@RequestBody @Valid CreateMemberInDto response) {

        CreateMemberOutDto request = memberService.signUp(response);

        return ResponseUtils.ok
                (Success.builder()
                        .code(SuccessCode.CREATED)
                        .data(request)
                        .build());
    }

}
