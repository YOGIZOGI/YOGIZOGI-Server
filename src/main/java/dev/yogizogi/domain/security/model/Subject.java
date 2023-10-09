package dev.yogizogi.domain.security.model;

import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Subject {

    private UUID id;
    private String phoneNumber;
    private TokenType type;

    @Builder
    public Subject(UUID id, String phoneNumber, TokenType type) {
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.type = type;
    }

}