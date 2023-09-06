package dev.yogizogi.domain.member.model.dto.response;

import dev.yogizogi.domain.member.model.entity.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Schema(name = "회원가입 응답 Dto")
@Getter
@Builder(access = AccessLevel.PRIVATE)
public class CreateMemberOutDto {

    @Schema(description = "식별자")
    private UUID id;

    @Schema(description = "아이디", example = "yogizogi")
    private String accountName;

    public static CreateMemberOutDto of(Member member) {
        return CreateMemberOutDto.builder()
                .id(member.getId())
                .accountName(member.getAccountName())
                .build();
    }

}
