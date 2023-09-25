package dev.yogizogi.domain.user.factory.dto;

import static dev.yogizogi.domain.user.factory.fixtures.UserFixtures.*;

import dev.yogizogi.domain.user.model.dto.request.CreateUserInDto;

public class CreateUserFactory {

    public static CreateUserInDto createUserInDto() {
        return new CreateUserInDto(계정, 비밀번호, 닉네임, 핸드폰번호);
    }

}
