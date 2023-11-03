package dev.yogizogi.infra.aws.s3;

import dev.yogizogi.global.common.model.response.Success;
import dev.yogizogi.global.util.ResponseUtils;
import dev.yogizogi.infra.aws.s3.model.request.IssuePreSignedInDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "S3 관련 API")
@RestController
@Validated
@RequestMapping("/api/s3")
@RequiredArgsConstructor

public class S3ApiController {

    private final S3Service s3Service;

    @Operation(summary = "객체 업로드를 위한 주소 발행", description = "사진과 동영상 등을 저장하기 위해 미리 서명된 주소를 받는다.")
    @PostMapping("/pre-signed")
    public ResponseEntity issueSignedUrl(@RequestBody IssuePreSignedInDto req) {
        return ResponseUtils.ok(
                Success.builder()
                        .data(s3Service.IssuePreSignedUrl(req.getId(), req.getNumber(), req.getDirectory()))
                        .build()
        );
    }


}
