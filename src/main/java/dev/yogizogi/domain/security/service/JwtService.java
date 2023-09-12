package dev.yogizogi.domain.security.service;

import static dev.yogizogi.domain.security.model.TokenType.ACCESS_TOKEN;
import static dev.yogizogi.domain.security.model.TokenType.REFRESH_TOKEN;
import static dev.yogizogi.global.common.code.ErrorCode.EXPIRED_TOKEN;
import static dev.yogizogi.global.common.code.ErrorCode.NOT_EXIST_ACCOUNT;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.yogizogi.domain.auth.model.dto.response.ReissueAccessTokenOutDto;
import dev.yogizogi.domain.member.exception.NotExistAccountException;
import dev.yogizogi.domain.member.repository.MemberRepository;
import dev.yogizogi.domain.security.exception.ExpiredTokenException;
import dev.yogizogi.domain.security.exception.FailToExtractSubjectException;
import dev.yogizogi.domain.security.exception.FailToSetClaimsException;
import dev.yogizogi.domain.member.model.entity.Member;
import dev.yogizogi.global.common.code.ErrorCode;
import dev.yogizogi.domain.security.model.Subject;
import dev.yogizogi.domain.security.model.TokenType;
import dev.yogizogi.global.util.RedisUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.transaction.Transactional;
import java.util.Date;
import java.util.Objects;
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
    private final MemberRepository memberRepository;
    private final CustomUserDetailsService userDetailsService;
    private final RedisUtils redisUtils;
    private final ObjectMapper objectMapper;

    /**
     * ACCESS 토큰 생성
     */
    public String createAccessToken(Member member) {
        String accessToken = issueToken(member.getId(), member.getAccountName(), ACCESS_TOKEN);
        return accessToken;
    }

    /**
     *  REFRESH 토큰 생성
     */
    public String createRefreshToken(Member member) {
        String accountName = member.getAccountName();
        String refreshToken = issueToken(member.getId(), accountName, REFRESH_TOKEN);
        redisUtils.saveWithExpirationTime(accountName, refreshToken, REFRESH_TOKEN.getExpirationTime());
        return refreshToken;
    }

    /**
     * ACCESS 토큰 재발급
     */
    public ReissueAccessTokenOutDto reissueAccessToken(String refreshToken) {

        Subject subject = extractSubject(refreshToken);
        Member findMember = memberRepository.findByAccountName(subject.getAccountName())
                .orElseThrow(() -> new NotExistAccountException(NOT_EXIST_ACCOUNT));

        if (Objects.isNull(redisUtils.findByKey(subject.getAccountName()))) {
            throw new ExpiredTokenException(EXPIRED_TOKEN);
        }

        return ReissueAccessTokenOutDto.of(
                findMember.getId(),
                issueToken(findMember.getId(), findMember.getAccountName(), ACCESS_TOKEN)
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

    /**
     * 토큰 발행
     * Token Type은 매개 변수를 통해 받아 지정
     */
    private String issueToken(UUID id, String email, TokenType type) {

        Date now = new Date();
        Claims claims = setClaims(id, email, type);
        Date expirationTime = setExpirationTime(now, type.getExpirationTime());

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expirationTime)
                .signWith(SignatureAlgorithm.HS512, secretKey)
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

        Date expirationDate = new Date(now.getTime() + expirationTime);
        return expirationDate;

    }

    /**
     *  Claims 추출
     */
    private Jws<Claims> extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token);
    }



}
