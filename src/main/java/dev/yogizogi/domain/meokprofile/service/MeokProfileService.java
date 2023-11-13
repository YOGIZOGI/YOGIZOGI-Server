package dev.yogizogi.domain.meokprofile.service;

import dev.yogizogi.domain.meokprofile.model.dto.request.CreateMeokProfileInDto;
import dev.yogizogi.domain.meokprofile.model.dto.response.CreateMeokProfileOutDto;
import dev.yogizogi.domain.meokprofile.model.entity.Intensity;
import dev.yogizogi.domain.meokprofile.model.entity.MeokProfile;
import dev.yogizogi.domain.meokprofile.model.entity.Preference;
import dev.yogizogi.domain.meokprofile.repository.MeokProfileRepository;
import dev.yogizogi.domain.user.exception.NotExistUserException;
import dev.yogizogi.domain.user.model.entity.FirstLoginStatus;
import dev.yogizogi.domain.user.model.entity.User;
import dev.yogizogi.domain.user.repository.UserRepository;
import dev.yogizogi.global.common.code.ErrorCode;
import dev.yogizogi.global.common.status.BaseStatus;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
@RequiredArgsConstructor
public class MeokProfileService {

    private final UserRepository userRepository;
    private final MeokProfileRepository meokProfileRepository;

    public CreateMeokProfileOutDto createMeokProfile(
            UUID userId, long spicyPreference, long spicyIntensity,
            long saltyPreference, long saltyIntensity, long sweetnessPreference, long sweetnessIntensity) {

        User findUser = userRepository.findByIdAndStatus(userId, BaseStatus.ACTIVE)
                .orElseThrow(() -> new NotExistUserException(ErrorCode.NOT_EXIST_USER));

        MeokProfile meokProfile = CreateMeokProfileInDto.toEntity(
                createPreference(spicyPreference, saltyPreference, sweetnessPreference),
                createIntensity(spicyIntensity, saltyIntensity, sweetnessIntensity)
        );

        meokProfileRepository.save(meokProfile);

        findUser.updateFirstLoginStatus(FirstLoginStatus.INACTIVE);
        findUser.setMeokProfile(meokProfile);

        return CreateMeokProfileOutDto.of(
                meokProfile.getPreference(), meokProfile.getIntensity()
        );

    }

    private static Intensity createIntensity(long spicyIntensity, long saltyIntensity, long sweetnessIntensity) {
        return Intensity.builder()
                .spicyIntensity(spicyIntensity)
                .saltyIntensity(saltyIntensity)
                .sweetnessIntensity(sweetnessIntensity)
                .build();
    }

    private static Preference createPreference(long spicyPreference, long saltyPreference, long sweetnessPreference) {
        return Preference.builder()
                .spicyPreference(spicyPreference)
                .saltyPreference(saltyPreference)
                .sweetnessPreference(sweetnessPreference)
                .build();
    }


}
