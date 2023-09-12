package dev.yogizogi.domain.security.service;

import static dev.yogizogi.domain.security.model.TokenType.ACCESS_TOKEN;
import static dev.yogizogi.domain.security.model.TokenType.REFRESH_TOKEN;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.yogizogi.domain.member.repository.MemberRepository;
import dev.yogizogi.domain.security.exception.FailSetClaimsException;
import dev.yogizogi.domain.member.model.entity.Member;
import dev.yogizogi.global.common.code.ErrorCode;
import dev.yogizogi.domain.security.model.Subject;
import dev.yogizogi.domain.security.model.TokenType;
import dev.yogizogi.global.util.RedisUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.transaction.Transactional;
import java.util.Date;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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
    private final RedisUtils redisUtils;
    private final ObjectMapper objectMapper;

    /**
     * 어세스 토큰 생성
     */
    public String createAccessToken(Member member) {
        String accessToken = issueToken(member.getAccountName(), ACCESS_TOKEN);
        return accessToken;
    }

    /**
     *  리프레쉬 토큰 생성
     */
    public String createRefreshToken(Member member) {
        String accountName = member.getAccountName();
        String refreshToken = issueToken(accountName, REFRESH_TOKEN);
        redisUtils.saveWithExpirationTime(accountName, refreshToken, REFRESH_TOKEN.getExpirationTime());
        return refreshToken;
    }

    /**
     * 토큰 발행
     * Token Type은 매개 변수를 통해 받아 지정
     */
    private String issueToken(String email, TokenType type) {

        Date now = new Date();
        Claims claims = setClaims(email, type);
        Date expirationTime = setExpirationTime(now, type.getExpirationTime());

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expirationTime)
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();

    }

    private Claims setClaims(String accountName, TokenType type) {

        Subject subject = Subject.builder()
                .accountName(accountName)
                .type(type).build();

        Claims claims;
        try {
             claims = Jwts.claims()
                    .setSubject(objectMapper.writeValueAsString(subject));
        } catch (JsonProcessingException e) {
            throw new FailSetClaimsException(ErrorCode.FAIL_TO_CREATE_JWT);
        }

        return claims;

    }

    private Date setExpirationTime(Date now, long expirationTime) {

        Date expirationDate = new Date(now.getTime() + expirationTime);
        return expirationDate;

    }

}
