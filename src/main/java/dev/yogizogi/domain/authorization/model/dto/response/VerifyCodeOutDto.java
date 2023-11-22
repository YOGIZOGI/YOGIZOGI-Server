package dev.yogizogi.domain.authorization.model.dto.response;

import dev.yogizogi.global.common.status.VerificationStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Schema(name = "인증코드 확인 응답 DTO")
@Getter
@Builder(access = AccessLevel.PRIVATE)
public class VerifyCodeOutDto {

    @Schema(description = "결과", allowableValues = {"PASS", "NOT_PASS"})
    private VerificationStatus status;

    @Schema(description = "입력한 핸드폰 번호")
    private String phoneNumber;

    public static VerifyCodeOutDto of(VerificationStatus status, String phoneNumber) {
        return VerifyCodeOutDto.builder()
                .status(status)
                .phoneNumber(phoneNumber)
                .build();
    }
}
