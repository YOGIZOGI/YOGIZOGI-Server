package dev.yogizogi.domain.meokprofile.factory.entity;

import static dev.yogizogi.domain.meokprofile.factory.fixtures.MeokProfileFixtures.단맛_선호도;
import static dev.yogizogi.domain.meokprofile.factory.fixtures.MeokProfileFixtures.매운맛_선호도;
import static dev.yogizogi.domain.meokprofile.factory.fixtures.MeokProfileFixtures.짠맛_선호도;

import dev.yogizogi.domain.meokprofile.model.entity.MeokProfile;
import dev.yogizogi.domain.meokprofile.model.entity.Preference;

public class MeokProfileFactory {

    public static MeokProfile createMeokProfile() {

        return MeokProfile.builder()
                .preference(Preference.builder()
                        .spicyPreference(매운맛_선호도)
                        .saltyPreference(짠맛_선호도)
                        .sweetnessPreference(단맛_선호도)
                        .build())
                .build();

    }

}
