package dev.yogizogi.infra.s3;

import static dev.yogizogi.global.common.model.constant.Format.S3_OBJECT_PATH;
import static dev.yogizogi.global.common.model.constant.Number.PRE_SIGNED_URL_EXPIRATION_TIME;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.Headers;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import dev.yogizogi.infra.s3.model.response.IssuePreSignedOutDto;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class S3Service {

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    /**
     * 파일 저장 1. Pre-Signed Url 생성 -> 2. object-key를 해당하는 객체 테이블에 저장
     */
    public IssuePreSignedOutDto IssuePreSignedUrl(String id, Long number, String directory) {

        log.info("-----Start To Create Pre-signed Url-----");
        StringBuilder path = new StringBuilder(String.format(S3_OBJECT_PATH, directory, id));

        // object-key 생성
        List<String> objectKeys = createObjectKeys(number, path);

        // pre-signed URL 생성
        List<String> preSignedUrls = createPreSignedUrls(objectKeys);


        log.info("-----Complete To Create Pre-signed Url-----");

        return IssuePreSignedOutDto.of(preSignedUrls);

    }

    private List<String> createObjectKeys(Long number, StringBuilder path) {

        List<String> objectKeys = new ArrayList<>();

        for (int i = 0; i < number; i++) {
            objectKeys.add(String.valueOf(path.append(UUID.randomUUID())));
        }

        return objectKeys;

    }

    private List<String> createPreSignedUrls(List<String> objectKeys) {

        List<String> preSignedUrls = new ArrayList<>();
        Date expirationDate = getExpiration(PRE_SIGNED_URL_EXPIRATION_TIME);

        for (String objectKey : objectKeys) {
            GeneratePresignedUrlRequest generatePresignedUrlRequest
                    = new GeneratePresignedUrlRequest(bucket, objectKey)
                    .withMethod(HttpMethod.PUT)
                    .withExpiration(expirationDate);

            generatePresignedUrlRequest.addRequestParameter(Headers.S3_CANNED_ACL,
                    CannedAccessControlList.PublicRead.toString());
            URL preSignedUrl = amazonS3.generatePresignedUrl(generatePresignedUrlRequest);

            preSignedUrls.add(preSignedUrl.toString());
        }

        return preSignedUrls;

    }

    private Date getExpiration(Long expirationTime) {

        Date now = new Date();
        return new Date(now.getTime() + expirationTime);

    }

}