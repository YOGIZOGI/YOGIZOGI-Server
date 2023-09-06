package dev.yogizogi.domain.auth.model.dto.response;

import dev.yogizogi.global.common.status.VerificationStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Schema(name = "인증코드 확인 응답 Dto")
@Getter
@Builder(access = AccessLevel.PRIVATE)
public class VerifyCodeOutDto {

    private VerificationStatus status;
    private String phoneNumber;

    public static VerifyCodeOutDto of(VerificationStatus status, String phoneNumber) {
        return VerifyCodeOutDto.builder()
                .status(status)
                .phoneNumber(phoneNumber)
                .build();
    }
}
