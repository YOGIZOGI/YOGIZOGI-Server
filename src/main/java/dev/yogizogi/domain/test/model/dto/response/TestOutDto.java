package dev.yogizogi.domain.test.model.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class TestOutDto {

    private String result;

    @Builder
    public static TestOutDto of(String result) {
        return TestOutDto.builder()
                .result(result)
                .build();
    }

}
