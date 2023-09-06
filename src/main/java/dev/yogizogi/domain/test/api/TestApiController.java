package dev.yogizogi.domain.test.api;

import dev.yogizogi.domain.test.model.dto.response.TestOutDto;
import dev.yogizogi.domain.test.service.TestService;
import dev.yogizogi.global.common.model.response.ApiResponse;
import dev.yogizogi.global.util.ResponseUtils;
import dev.yogizogi.global.common.model.response.Success;
import dev.yogizogi.global.common.code.SuccessCode;
import lombok.RequiredArgsConstructor;
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
    public ApiResponse testConnection() {

        TestOutDto result = testService.testConnection();

        return ResponseUtils.ok(Success.<TestOutDto>builder()
                .code(SuccessCode.SUCCESS_TEST)
                .data(result)
                .build());

    }

    @GetMapping("/exception")
    public ApiResponse testException(@RequestParam String code) {

        TestOutDto result = testService.testException(code);

        return ResponseUtils.ok(Success.<TestOutDto>builder()
                .code(SuccessCode.SUCCESS_TEST)
                .data(result)
                .build());

    }


}
