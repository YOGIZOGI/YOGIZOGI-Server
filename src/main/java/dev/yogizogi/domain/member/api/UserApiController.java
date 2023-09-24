package dev.yogizogi.domain.member.api;

import dev.yogizogi.domain.member.service.UserService;
import dev.yogizogi.global.common.model.response.Success;
import dev.yogizogi.global.util.ResponseUtils;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "유저 관련 API")
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;


    @Parameter(name = "accountName", description = "삭제할 계정")
    @PutMapping("/delete")
    public ResponseEntity deleteUser(@RequestParam String accountName) {
        return ResponseUtils.ok(
                Success.builder()
                        .data(userService.deleteUser(accountName))
                        .build()
        );
    }


}
