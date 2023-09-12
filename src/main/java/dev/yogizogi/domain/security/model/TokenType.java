package dev.yogizogi.domain.security.model;

import java.time.Duration;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TokenType {

    ACCESS_TOKEN("ACCESS_TOKEN", Duration.ofMinutes(1).toMillis()),
    REFRESH_TOKEN("REFRESH_TOKEN", Duration.ofDays(365).toMillis());

    private final String name;
    private final long expirationTime;

}