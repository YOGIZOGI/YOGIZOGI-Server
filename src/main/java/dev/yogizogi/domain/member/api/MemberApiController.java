package dev.yogizogi.domain.member.api;

import dev.yogizogi.domain.member.model.dto.request.CreateMemberInDto;
import dev.yogizogi.domain.member.service.MemberService;
import dev.yogizogi.global.common.model.response.Result;
import dev.yogizogi.global.common.model.response.Success;
import dev.yogizogi.global.util.ResponseUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity createMember(@RequestBody @Valid CreateMemberInDto response) {

        return ResponseUtils.created(
                Success.builder()
                        .data(memberService.signUp(response))
                        .build());

    }

}
