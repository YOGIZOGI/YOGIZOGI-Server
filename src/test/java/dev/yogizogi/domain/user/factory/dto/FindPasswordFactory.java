package dev.yogizogi.domain.user.factory.dto;

import static dev.yogizogi.domain.user.factory.fixtures.UserFixtures.핸드폰_번호;

import dev.yogizogi.domain.user.model.dto.response.FindPasswordOutDto;
import dev.yogizogi.global.common.status.MessageStatus;

public class FindPasswordFactory {

    public static FindPasswordOutDto findPasswordOutDto() {
        return FindPasswordOutDto.of(MessageStatus.SUCCESS, 핸드폰_번호);
    }


}
