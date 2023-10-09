package dev.yogizogi.domain.user.model.dto.response;

import dev.yogizogi.global.common.status.MessageStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Schema(name = "비밀번호 찾기 응답 Dto")
@Getter
@AllArgsConstructor
@Builder(access = AccessLevel.PRIVATE)
public class FindPasswordOutDto {

    @Schema(description = "전송 결과", example = "SUCCESS", allowableValues = {"SUCCESS", "FAIL"})
    private MessageStatus status;

    @Schema(description = "계정", example = "01012345678")
    private String phoneNumber;

    public static FindPasswordOutDto of(MessageStatus status, String phoneNumber) {
        return FindPasswordOutDto.builder()
                .status(status)
                .phoneNumber(phoneNumber)
                .build();
    }

}