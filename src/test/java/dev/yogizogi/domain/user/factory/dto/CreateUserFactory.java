package dev.yogizogi.domain.user.factory.dto;

import static dev.yogizogi.domain.user.factory.fixtures.UserFixtures.*;
import static dev.yogizogi.domain.user.factory.fixtures.PasswordFixtures.*;

import dev.yogizogi.domain.user.model.dto.request.CreateUserInDto;
import dev.yogizogi.domain.user.model.dto.response.CreateUserOutDto;

public class CreateUserFactory {

    public static CreateUserInDto createUserInDto() {
        return new CreateUserInDto(핸드폰_번호, 비밀번호);
    }

    public static CreateUserOutDto createUserOutDto() {
        return CreateUserOutDto.of(식별자, 핸드폰_번호);
    }

}
