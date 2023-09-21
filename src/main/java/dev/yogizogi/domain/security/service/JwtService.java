package dev.yogizogi.domain.security.service;

import static dev.yogizogi.domain.security.model.TokenType.ACCESS_TOKEN;
import static dev.yogizogi.domain.security.model.TokenType.REFRESH_TOKEN;
import static dev.yogizogi.global.common.code.ErrorCode.EXPIRED_TOKEN;
import static dev.yogizogi.global.common.code.ErrorCode.NOT_EXIST_ACCOUNT;
import static dev.yogizogi.global.common.model.constant.Format.TOKEN_HEADER_NAME;
import static dev.yogizogi.global.common.model.constant.Format.TOKEN_PREFIX;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.yogizogi.domain.auth.model.dto.response.ReissueAccessTokenOutDto;
import dev.yogizogi.domain.member.exception.NotExistAccountException;
import dev.yogizogi.domain.member.repository.UserRepository;
import dev.yogizogi.domain.security.exception.ExpiredTokenException;
import dev.yogizogi.domain.security.exception.FailToExtractSubjectException;
import dev.yogizogi.domain.security.exception.FailToSetClaimsException;
import dev.yogizogi.domain.member.model.entity.User;
import dev.yogizogi.domain.security.exception.InvalidTokenException;
import dev.yogizogi.domain.security.model.CustomUserDetails;
import dev.yogizogi.global.common.code.ErrorCode;
import dev.yogizogi.domain.security.model.Subject;
import dev.yogizogi.domain.security.model.TokenType;
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
    public String createAccessToken(User user) {
        return TOKEN_PREFIX.concat(issueToken(user.getId(), user.getAccountName(), ACCESS_TOKEN));
    }

    /**
     *  REFRESH 토큰 생성
     */
    public String createRefreshToken(User user) {
        String refreshToken = issueToken(user.getId(), user.getAccountName(), REFRESH_TOKEN);
        redisUtils.saveWithExpirationTime(user.getAccountName(), refreshToken, REFRESH_TOKEN.getExpirationTime());
        return TOKEN_PREFIX.concat(refreshToken);
    }

    /**
     * ACCESS 토큰 재발급
     */
    public ReissueAccessTokenOutDto reissueAccessToken(UUID id, String accountName) {

        User findUser = userRepository.findByIdAndAccountName(id, accountName)
                .orElseThrow(() -> new NotExistAccountException(NOT_EXIST_ACCOUNT));

        String refreshToken = redisUtils.findByKey(findUser.getAccountName());
        if (Objects.isNull(refreshToken)) {
            throw new ExpiredTokenException(EXPIRED_TOKEN);
        }

        return ReissueAccessTokenOutDto.of(
                findUser.getId(),
                issueToken(findUser.getId(), findUser.getAccountName(), ACCESS_TOKEN)
        );

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

    private String issueToken(UUID id, String email, TokenType type) {

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

    private Claims setClaims(UUID id, String accountName, TokenType type) {

        Subject subject = Subject.builder()
                .id(id)
                .accountName(accountName)
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
        return Optional.ofNullable(request.getHeader(TOKEN_HEADER_NAME).replace("Bearer ", ""));
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
        CustomUserDetails userDetails = userDetailsService.loadUserByUsername(extractSubject(token).getAccountName());
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }


}
