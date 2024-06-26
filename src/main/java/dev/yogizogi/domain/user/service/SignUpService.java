package dev.yogizogi.domain.user.service;

import dev.yogizogi.domain.meokmap.model.entity.MeokMap;
import dev.yogizogi.domain.meokmap.repository.MeokMapRepository;
import dev.yogizogi.domain.meokmap.service.MeokMapService;
import dev.yogizogi.domain.user.model.dto.request.CreateUserInDto;
import dev.yogizogi.domain.user.model.dto.response.CreateUserOutDto;
import dev.yogizogi.domain.user.model.entity.Authority;
import dev.yogizogi.domain.user.model.entity.FirstLoginStatus;
import dev.yogizogi.domain.user.model.entity.User;
import dev.yogizogi.domain.user.repository.UserRepository;
import dev.yogizogi.global.util.UuidUtils;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class SignUpService {

    private final MeokMapService meokMapService;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final MeokMapRepository meokMapRepository;

    public CreateUserOutDto signUp(String phoneNumber, String password) {

        User newUser =
                CreateUserInDto.toEntity(
                        UuidUtils.createSequentialUUID(),
                        phoneNumber,
                        passwordEncoder.encode(password),
                        FirstLoginStatus.ACTIVE
                );

        newUser.setRoles(Collections.singletonList(Authority.builder().name("ROLE_USER").build()));
        userRepository.save(newUser);

        MeokMap meokMap = MeokMap.builder()
                .user(newUser)
                .build();

        meokMapRepository.save(meokMap);

        return CreateUserOutDto.of(newUser.getId(), newUser.getPhoneNumber());

    }

}