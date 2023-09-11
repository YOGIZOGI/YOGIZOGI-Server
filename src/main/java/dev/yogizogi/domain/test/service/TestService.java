package dev.yogizogi.domain.test.service;

import dev.yogizogi.domain.test.model.dto.response.TestOutDto;
import dev.yogizogi.global.common.code.ErrorCode;
import dev.yogizogi.global.common.exception.BaseException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TestService {

    public TestOutDto testConnection() {
            return TestOutDto.of("연결 완료");
    }

    public TestOutDto testException(String code) {

        if  (code.equals("실패")) {
            throw new BaseException(ErrorCode.FAIL_TO_TEST);
        }

        return TestOutDto.of("연결 완료");

    }

}
