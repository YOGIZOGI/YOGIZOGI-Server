package dev.yogizogi.domain.meokprofile.factory.dto;

import static dev.yogizogi.domain.meokprofile.factory.fixtures.MeokProfileFixtures.*;

import java.util.List;

import dev.yogizogi.domain.meokprofile.model.dto.request.CreateMeokProfileInDto;
import dev.yogizogi.domain.meokprofile.model.dto.response.CreateMeokProfileOutDto;
import dev.yogizogi.domain.meokprofile.model.entity.Preference;

public class CreateMeokProfileFactory {

    public static CreateMeokProfileInDto createMeokProfileInDto() {

        return new CreateMeokProfileInDto(List.of("맵고수", "민초단") ,매운맛_선호도, 짠맛_선호도, 단맛_선호도);

    }

    public static CreateMeokProfileOutDto createMeokProfileOutDto() {

        return CreateMeokProfileOutDto.of(
                먹태그,
                Preference.builder()
                        .spicyPreference(매운맛_선호도)
                        .saltyPreference(짠맛_선호도)
                        .sweetnessPreference(단맛_선호도)
                        .build()
        );

    }

}
