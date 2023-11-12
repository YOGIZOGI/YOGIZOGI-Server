package dev.yogizogi.domain.user.factory.entity;

import dev.yogizogi.domain.user.factory.fixtures.ProfileFixtures;
import dev.yogizogi.domain.user.model.entity.Profile;

public class ProfileFactory {

    public static Profile createProfile() {

        Profile profile = Profile.builder()
                .nickname(ProfileFixtures.등록할_닉네임)
                .introduction(ProfileFixtures.등록할_소개)
                .imageUrl(ProfileFixtures.프로필_사진)
                .build();

        return profile;
    }

}
