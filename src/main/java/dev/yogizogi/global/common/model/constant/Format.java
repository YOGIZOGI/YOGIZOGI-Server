package dev.yogizogi.global.common.model.constant;

public class Format {

    public static final String VALID_ERROR_RESULT = "Error : [%s] / Field : [%s] / Input : [%s]";
    public static final String VALIDATED_ERROR_RESULT = "Error : [%s] / Input : [%s]";
    public static final String VERIFICATION_CODE_MESSAGE = "[YOGIZOGI] 본인 확인을 위해 [%s]을(를) 입력해주세요.\n";
    public static final String TOKEN_HEADER_NAME = "Authorization";
    public static final String REISSUE_ACCESS_TOKEN_URL = "/api/auth/reissue-access-token";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String DONE = "수행 완료";
    public static final String S3_OBJECT_PATH = "%s/%s/";
}
