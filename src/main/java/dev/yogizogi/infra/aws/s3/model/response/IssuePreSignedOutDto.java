package dev.yogizogi.infra.aws.s3.model.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Schema(name = "객체 업로드 주소 발행 응답 Dto", description = "발행한 주소에 대한 정보")
@Getter
@Builder(access = AccessLevel.PROTECTED)
public class IssuePreSignedOutDto {

    @Schema(description = "발행한 주소")
    List<String> preSignedUrl;

    public static IssuePreSignedOutDto of(List<String> preSignedUrls) {
        return IssuePreSignedOutDto.builder()
                .preSignedUrl(preSignedUrls)
                .build();
    }
}
