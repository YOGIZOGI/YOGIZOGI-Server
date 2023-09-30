package dev.yogizogi.domain.auth.factory.dto;

import static dev.yogizogi.domain.security.factory.fixtures.TokenFixtures.리프레쉬_토큰;
import static dev.yogizogi.domain.security.factory.fixtures.TokenFixtures.어세스_토큰;
import static dev.yogizogi.domain.user.factory.fixtures.UserFixtures.계정;
import static dev.yogizogi.domain.user.factory.fixtures.UserFixtures.비밀번호;
import static dev.yogizogi.domain.user.factory.fixtures.UserFixtures.식별자;

import dev.yogizogi.domain.auth.model.dto.request.LoginInDto;
import dev.yogizogi.domain.auth.model.dto.response.LoginOutDto;

public class LoginFactory {

    public static LoginInDto LoginInDto() {return new LoginInDto(계정, 비밀번호);}

    public static LoginOutDto LoginOutDto() {
        return LoginOutDto.of(식별자, 계정, 어세스_토큰, 리프레쉬_토큰);
    }

}
