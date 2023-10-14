package dev.yogizogi.domain.security.service;

import static dev.yogizogi.domain.security.model.TokenType.ACCESS_TOKEN;
import static dev.yogizogi.domain.security.model.TokenType.REFRESH_TOKEN;
import static dev.yogizogi.global.common.code.ErrorCode.EXPIRED_TOKEN;
import static dev.yogizogi.global.common.code.ErrorCode.NOT_EXIST_PHONE_NUMBER;
import static dev.yogizogi.global.common.model.constant.Format.TOKEN_HEADER_NAME;
import static dev.yogizogi.global.common.model.constant.Format.TOKEN_PREFIX;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.yogizogi.domain.authorization.model.dto.response.ReissueAccessTokenOutDto;
import dev.yogizogi.domain.security.exception.EmptyTokenException;
import dev.yogizogi.domain.security.exception.ExpiredTokenException;
import dev.yogizogi.domain.security.exception.FailToExtractSubjectException;
import dev.yogizogi.domain.security.exception.FailToSetClaimsException;
import dev.yogizogi.domain.security.exception.InvalidTokenException;
import dev.yogizogi.domain.security.model.CustomUserDetails;
import dev.yogizogi.domain.security.model.Subject;
import dev.yogizogi.domain.security.model.TokenType;
import dev.yogizogi.domain.user.exception.NotExistPhoneNumberException;
import dev.yogizogi.domain.user.model.entity.User;
import dev.yogizogi.domain.user.repository.UserRepository;
import dev.yogizogi.global.common.code.ErrorCode;
import dev.yogizogi.global.common.status.BaseStatus;
import dev.yogizogi.global.util.RedisUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Slf4j
@Getter
@Service
@Transactional
@RequiredArgsConstructor
public class JwtService {

    @Value("${jwt.secretKey}")
    private String secretKey;

    private final UserRepository userRepository;
    private final CustomUserDetailsService userDetailsService;
    private final RedisUtils redisUtils;
    private final ObjectMapper objectMapper;

    /**
     * ACCESS 토큰 생성
     */
    public String issueAccessToken(UUID id, String phoneNumber) {
        return TOKEN_PREFIX.concat(createToken(id, phoneNumber, ACCESS_TOKEN));
    }

    /**
     *  REFRESH 토큰 생성
     */
    public String issueRefreshToken(UUID id, String phoneNumber) {
        String refreshToken = createToken(id, phoneNumber, REFRESH_TOKEN);
        redisUtils.saveWithExpirationTime(phoneNumber, refreshToken, REFRESH_TOKEN.getExpirationTime());
        return TOKEN_PREFIX.concat(refreshToken);
    }

    /**
     * ACCESS 토큰 재발급
     */
    public ReissueAccessTokenOutDto reissueAccessToken(UUID id, String phoneNumber) {

        User findUser = userRepository.findByIdAndPhoneNumberAndStatus(id, phoneNumber, BaseStatus.ACTIVE)
                .orElseThrow(() -> new NotExistPhoneNumberException(NOT_EXIST_PHONE_NUMBER));

        checkRefreshToken(findUser.getPhoneNumber());

        return ReissueAccessTokenOutDto.of(
                findUser.getId(),
                issueAccessToken(findUser.getId(), findUser.getPhoneNumber())
        );

    }

    private String checkRefreshToken(String phoneNumber) {

        String refreshToken = redisUtils.findByKey(phoneNumber);

        if (Objects.isNull(refreshToken)) {
            throw new ExpiredTokenException(EXPIRED_TOKEN);
        }

        return refreshToken;

    }

    /**
     * Subject 추출
     */
    public Subject extractSubject(String token) {

        String subject = extractClaims(token).getBody().getSubject();

        try {
            return objectMapper.readValue(subject, Subject.class);
        } catch (JsonProcessingException e) {
            throw new FailToExtractSubjectException(ErrorCode.FAIL_TO_EXTRACT_SUBJECT);
        }

    }

    private String createToken(UUID id, String email, TokenType type) {

        Date now = new Date();
        Claims claims = setClaims(id, email, type);
        Date expirationTime = setExpirationTime(now, type.getExpirationTime());

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expirationTime)
                .signWith(
                        Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)),
                        SignatureAlgorithm.HS512
                )
                .compact();

    }

    private Claims setClaims(UUID id, String phoneNumber, TokenType type) {

        Subject subject = Subject.builder()
                .id(id)
                .phoneNumber(phoneNumber)
                .type(type).build();

        Claims claims;
        try {
            claims = Jwts.claims()
                    .setSubject(objectMapper.writeValueAsString(subject));
        } catch (JsonProcessingException e) {
            throw new FailToSetClaimsException(ErrorCode.FAIL_TO_CREATE_JWT);
        }

        return claims;

    }

    private Date setExpirationTime(Date now, long expirationTime) {

        return new Date(now.getTime() + expirationTime);

    }

    private Jws<Claims> extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(
                        Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8))
                )
                .build()
                .parseClaimsJws(token);
    }

    /**
     * 토큰 추출
     */
    public Optional<String> extractToken(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(TOKEN_HEADER_NAME).replace(TOKEN_PREFIX, ""));
    }

    /**
     * 토큰 검증
     */
    public boolean isValid(String token) {

        try {
            extractClaims(token);
            return true;
        } catch (ExpiredJwtException exception) {
            throw new ExpiredTokenException(EXPIRED_TOKEN);
        } catch (JwtException exception) {
            throw new InvalidTokenException(ErrorCode.INVALID_TOKEN);
        }
    }

    /**
     * 인증 등록
     */
    public Authentication getAuthentication(String token) {
        CustomUserDetails userDetails = userDetailsService.loadUserByUsername(extractSubject(token).getPhoneNumber());
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    /**
     * 토큰에서 유저 식별자 추출
     */
    public UUID getUserId() {

        HttpServletRequest req = getRequest();
        String accessToken = extractToken(req)
                .orElseThrow(() -> new EmptyTokenException(ErrorCode.EMPTY_TOKEN));

        Subject subject = extractSubject(accessToken);

        return subject.getId();

    }

    private HttpServletRequest getRequest() {
        return ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
    }


}
