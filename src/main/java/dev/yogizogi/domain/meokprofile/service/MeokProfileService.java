package dev.yogizogi.domain.meokprofile.service;

import dev.yogizogi.domain.meokprofile.model.dto.request.CreateMeokProfileInDto;
import dev.yogizogi.domain.meokprofile.model.dto.response.CreateMeokProfileOutDto;
import dev.yogizogi.domain.meokprofile.model.entity.MeokProfile;
import dev.yogizogi.domain.meokprofile.repository.MeokProfileRepository;
import dev.yogizogi.domain.user.exception.NotExistPhoneNumberException;
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
                .orElseThrow(() -> new NotExistPhoneNumberException(ErrorCode.NOT_EXIST_PHONE_NUMBER));

        findUser.setFirstLoginStatus(FirstLoginStatus.INACTIVE);

        MeokProfile meokProfile = CreateMeokProfileInDto.toEntity(
                findUser, spicyPreference, spicyIntensity, saltyPreference,
                saltyIntensity, sweetnessPreference, sweetnessIntensity
        );

        meokProfile = meokProfileRepository.save(meokProfile);

        return CreateMeokProfileOutDto.of(meokProfile.getId());

    }


}
