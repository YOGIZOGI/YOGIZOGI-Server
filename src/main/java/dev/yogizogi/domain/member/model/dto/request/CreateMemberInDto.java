package dev.yogizogi.domain.member.model.dto.request;

import dev.yogizogi.domain.member.model.entity.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Schema(name = "회원가입 요청 Dto")
@Getter
public class CreateMemberInDto {

    @NotBlank(message="아이디를 입력하세요.")
    @Schema(description = "아이디", example = "yogizogi")
    private String accountName;

    @NotBlank(message="비밀번호를 입력하세요.")
    @Schema(description = "비밀번호", example = "yogi1234!")
    private String password;

    @NotBlank(message="닉네임을 입력하세요.")
    @Schema(description = "닉네임", example = "요기조기")
    private String nickName;

    @NotBlank(message="전화번호를 입력하세요.")
    @Pattern(regexp = "^010\\d{8}$", message = "올바른 전화번호 형식이 아닙니다.")
    @Schema(description = "핸드폰 번호", example = "01012345678")
    private String phoneNumber;

    public static Member toEntity(CreateMemberInDto createMemberInDto, String password){
        return Member.builder()
                .accountName(createMemberInDto.getAccountName())
                .password(password)
                .nickName(createMemberInDto.getNickName())
                .phoneNumber(createMemberInDto.getPhoneNumber())
                .build();
    }


}
