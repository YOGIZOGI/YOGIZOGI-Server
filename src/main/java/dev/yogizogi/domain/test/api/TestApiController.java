package dev.yogizogi.domain.test.api;

import dev.yogizogi.domain.test.service.TestService;
import dev.yogizogi.global.common.model.response.Result;
import dev.yogizogi.global.common.model.response.Success;
import dev.yogizogi.global.util.ResponseUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/test")
@RequiredArgsConstructor
@RestController
public class TestApiController {

    private final TestService testService;

    @GetMapping("/")
    public ResponseEntity testConnection() {

        return ResponseUtils.ok(
                Success.builder()
                        .data(testService.testConnection())
                        .build());
    }

    @GetMapping("/exception")
    public ResponseEntity testException(@RequestParam String code) {

        return ResponseUtils.ok(
                Success.builder()
                        .data(testService.testException(code))
                        .build());

    }


}
