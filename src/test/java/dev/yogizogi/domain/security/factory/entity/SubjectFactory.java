package dev.yogizogi.domain.security.factory.entity;

import static dev.yogizogi.domain.security.factory.fixtures.TokenFixtures.토큰에_포함할_핸드폰_번호;
import static dev.yogizogi.domain.security.factory.fixtures.TokenFixtures.토큰에_포함할_식별자;

import dev.yogizogi.domain.security.model.Subject;
import dev.yogizogi.domain.security.model.TokenType;

public class SubjectFactory {

    public static Subject extractSubject() {

        return Subject.builder()
                .id(토큰에_포함할_식별자)
                .phoneNumber(토큰에_포함할_핸드폰_번호)
                .type(TokenType.ACCESS_TOKEN)
                .build();

    }

}
