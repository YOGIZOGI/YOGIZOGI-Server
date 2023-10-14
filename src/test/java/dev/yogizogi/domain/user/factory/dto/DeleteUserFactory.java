package dev.yogizogi.domain.user.factory.dto;

import static dev.yogizogi.domain.user.factory.fixtures.UserFixtures.핸드폰_번호;

import dev.yogizogi.domain.user.model.dto.response.DeleteUserOutDto;
import dev.yogizogi.global.common.status.BaseStatus;

public class DeleteUserFactory {

    public static DeleteUserOutDto deleteUserOutDto() {
        return DeleteUserOutDto.of(핸드폰_번호, BaseStatus.INACTIVE);
    }

}
