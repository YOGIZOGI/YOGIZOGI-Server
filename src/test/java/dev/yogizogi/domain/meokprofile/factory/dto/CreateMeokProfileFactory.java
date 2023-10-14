package dev.yogizogi.domain.meokprofile.factory.dto;

import static dev.yogizogi.domain.meokprofile.factory.fixtures.MeokProfileFixtures.단맛_강도;
import static dev.yogizogi.domain.meokprofile.factory.fixtures.MeokProfileFixtures.단맛_선호도;
import static dev.yogizogi.domain.meokprofile.factory.fixtures.MeokProfileFixtures.매운맛_강도;
import static dev.yogizogi.domain.meokprofile.factory.fixtures.MeokProfileFixtures.매운맛_선호도;
import static dev.yogizogi.domain.meokprofile.factory.fixtures.MeokProfileFixtures.짠맛_강도;
import static dev.yogizogi.domain.meokprofile.factory.fixtures.MeokProfileFixtures.짠맛_선호도;

import dev.yogizogi.domain.meokprofile.model.dto.request.CreateMeokProfileInDto;
import dev.yogizogi.domain.meokprofile.model.dto.response.CreateMeokProfileOutDto;
import dev.yogizogi.domain.meokprofile.model.entity.Intensity;
import dev.yogizogi.domain.meokprofile.model.entity.Preference;

public class CreateMeokProfileFactory {

    public static CreateMeokProfileInDto createMeokProfileInDto() {

        return new CreateMeokProfileInDto(매운맛_선호도, 매운맛_강도, 짠맛_선호도, 짠맛_강도, 단맛_선호도, 단맛_강도);

    }

    public static CreateMeokProfileOutDto createMeokProfileOutDto() {

        return CreateMeokProfileOutDto.of(
                Preference.builder()
                        .spicyPreference(매운맛_선호도)
                        .saltyPreference(짠맛_선호도)
                        .sweetnessPreference(단맛_선호도)
                        .build(),
                Intensity.builder()
                        .spicyIntensity(매운맛_강도)
                        .saltyIntensity(짠맛_강도)
                        .sweetnessIntensity(단맛_강도)
                        .build()
        );

    }

}
