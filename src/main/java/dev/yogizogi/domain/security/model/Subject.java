package dev.yogizogi.domain.security.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Subject {

    private String accountName;
    private TokenType type;

    @Builder
    public Subject(String accountName, TokenType type) {
        this.accountName = accountName;
        this.type = type;
    }

}