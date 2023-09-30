package dev.yogizogi.domain.security.factory.dto;

import static dev.yogizogi.domain.security.factory.fixtures.TokenFixtures.발행한_어세스_토큰;
import static dev.yogizogi.domain.security.factory.fixtures.TokenFixtures.토큰에_포함된_식별자;
import static dev.yogizogi.domain.user.factory.fixtures.UserFixtures.계정;
import static dev.yogizogi.domain.user.factory.fixtures.UserFixtures.닉네임;
import static dev.yogizogi.domain.user.factory.fixtures.UserFixtures.비밀번호;
import static dev.yogizogi.domain.user.factory.fixtures.UserFixtures.식별자;
import static dev.yogizogi.domain.user.factory.fixtures.UserFixtures.핸드폰번호;

import dev.yogizogi.domain.auth.model.dto.response.ReissueAccessTokenOutDto;
import dev.yogizogi.domain.user.model.dto.request.CreateUserInDto;
import dev.yogizogi.domain.user.model.dto.response.CreateUserOutDto;

public class ReissueAccessTokenFactory {

    public static ReissueAccessTokenOutDto reissueAccessTokenOutDto() {
        return ReissueAccessTokenOutDto.of(토큰에_포함된_식별자, 발행한_어세스_토큰);
    }

}
