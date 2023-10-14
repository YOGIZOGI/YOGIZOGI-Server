package dev.yogizogi.domain.user.factory.dto;

import static dev.yogizogi.domain.user.factory.fixtures.ProfileFixtures.등록할_닉네임;
import static dev.yogizogi.domain.user.factory.fixtures.ProfileFixtures.등록할_소개;
import static dev.yogizogi.domain.user.factory.fixtures.ProfileFixtures.프로필_사진;

import dev.yogizogi.domain.user.model.dto.request.CreateUserProfileInDto;
import dev.yogizogi.domain.user.model.dto.response.CreateUserProfileOutDto;

public class CreateProfileFactory {

    public static CreateUserProfileInDto createUserProfileInDto() {
        return new CreateUserProfileInDto(등록할_닉네임, 프로필_사진, 등록할_소개);
    }

    public static CreateUserProfileOutDto createUserProfileOutDto() {
        return CreateUserProfileOutDto.of(등록할_닉네임, 프로필_사진, 등록할_소개);
    }

}
