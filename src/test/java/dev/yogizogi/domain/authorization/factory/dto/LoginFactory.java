package dev.yogizogi.domain.authorization.factory.dto;

import static dev.yogizogi.domain.authorization.factory.fixtures.LoginFixtures.로그인_한_유저;
import static dev.yogizogi.domain.authorization.factory.fixtures.LoginFixtures.받은_비밀번호;
import static dev.yogizogi.domain.authorization.factory.fixtures.LoginFixtures.받은_핸드폰_번호;
import static dev.yogizogi.domain.authorization.factory.fixtures.LoginFixtures.발행한_리프레쉬_토큰;
import static dev.yogizogi.domain.authorization.factory.fixtures.LoginFixtures.발행한_어세스_토큰;
import static dev.yogizogi.domain.authorization.factory.fixtures.LoginFixtures.처음_로그인;

import dev.yogizogi.domain.authorization.model.dto.request.LoginInDto;
import dev.yogizogi.domain.authorization.model.dto.response.LoginOutDto;

public class LoginFactory {

    public static LoginInDto LoginInDto() {
        return new LoginInDto(
                받은_핸드폰_번호,
                받은_비밀번호
        );
    }

    public static LoginOutDto LoginOutDto() {
        return LoginOutDto.of(
                로그인_한_유저,
                처음_로그인,
                발행한_어세스_토큰,
                발행한_리프레쉬_토큰);
    }

}
