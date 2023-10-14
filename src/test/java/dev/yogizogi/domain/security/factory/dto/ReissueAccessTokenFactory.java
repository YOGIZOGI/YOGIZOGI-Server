package dev.yogizogi.domain.security.factory.dto;

import static dev.yogizogi.domain.security.factory.fixtures.TokenFixtures.발행한_어세스_토큰;
import static dev.yogizogi.domain.security.factory.fixtures.TokenFixtures.토큰에_포함된_식별자;

import dev.yogizogi.domain.authorization.model.dto.response.ReissueAccessTokenOutDto;

public class ReissueAccessTokenFactory {

    public static ReissueAccessTokenOutDto reissueAccessTokenOutDto() {
        return ReissueAccessTokenOutDto.of(토큰에_포함된_식별자, 발행한_어세스_토큰);
    }

}
