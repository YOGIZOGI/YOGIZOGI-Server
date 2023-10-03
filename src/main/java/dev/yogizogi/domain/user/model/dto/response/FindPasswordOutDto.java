package dev.yogizogi.domain.user.model.dto.response;

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

    @Schema(description = "아이디", example = "yogizogi")
    private String accountName;

    public static FindPasswordOutDto of(String accountName) {
        return FindPasswordOutDto.builder()
                .accountName(accountName)
                .build();
    }

}