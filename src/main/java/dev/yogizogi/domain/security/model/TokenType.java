package dev.yogizogi.domain.security.model;

import java.time.Duration;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TokenType {

    ACCESS_TOKEN("Access", Duration.ofMinutes(60).toMillis()),
    REFRESH_TOKEN("Refresh", Duration.ofDays(365).toMillis());

    private final String name;
    private final long expirationTime;

}