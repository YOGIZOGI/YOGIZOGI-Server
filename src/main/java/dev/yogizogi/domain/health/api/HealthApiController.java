package dev.yogizogi.domain.health.api;

import dev.yogizogi.global.common.model.response.Success;
import dev.yogizogi.global.util.ResponseUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "서버 구동 확인 API")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class HealthApiController {

    @GetMapping("/test")
    public ResponseEntity check() {
        return ResponseUtils.ok(
                Success.builder()
                        .data(null)
                        .build());

    }

}
