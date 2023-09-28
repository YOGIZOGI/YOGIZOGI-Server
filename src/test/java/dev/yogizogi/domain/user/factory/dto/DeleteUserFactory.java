package dev.yogizogi.domain.user.factory.dto;

import static dev.yogizogi.domain.user.factory.fixtures.UserFixtures.계정;

import dev.yogizogi.domain.user.model.dto.response.DeleteUserOutDto;
import dev.yogizogi.global.common.status.BaseStatus;

public class DeleteUserFactory {

    public static DeleteUserOutDto deleteUserOutDto() {
        return new DeleteUserOutDto(계정, BaseStatus.INACTIVE);
    }

}
