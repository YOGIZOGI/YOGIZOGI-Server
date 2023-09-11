package dev.yogizogi.domain.security.service;

import static dev.yogizogi.domain.security.model.TokenType.ACCESS_TOKEN;
import static dev.yogizogi.domain.security.model.TokenType.REFRESH_TOKEN;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.util.Objects;
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

    private final RedisUtils redisUtils;
    private final ObjectMapper objectMapper;

    /**
     * 토큰 생성
     */
    public String createAccessToken(Member member) {

        log.info("-----Start To Create Token-----");

        String accountName = member.getAccountName();

        String accessToken = issueToken(accountName, ACCESS_TOKEN);

        if (Objects.isNull(redisUtils.findByKey(accountName))) {
            String refreshToken = issueToken(accountName, REFRESH_TOKEN);
            redisUtils.saveWithExpirationTime(accountName, refreshToken, REFRESH_TOKEN.getExpirationTime());
        }

        log.info("-----Complete To Create Token-----");

        return accessToken;

    }

    /**
     * 토큰 발행
     * Token Type은 매개 변수를 통해 받아 지정
     */
    private String issueToken(String email, TokenType type) {

        Date now = new Date();
        Claims claims = setClaims(email, type);
        Date expirationDate = getExpirationDate(now, type.getExpirationTime());

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();

    }

    private Claims setClaims(String accountName, TokenType type) {

        log.info("Type");
        log.info("-> " + type.getName());
        log.info("Email Information");
        log.info("-> " + accountName);

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

    private Date getExpirationDate(Date now, long expirationTime) {

        Date expirationDate = new Date(now.getTime() + expirationTime);

        log.info("Expiration Date");
        log.info("-> " + expirationDate);

        return expirationDate;

    }



}
