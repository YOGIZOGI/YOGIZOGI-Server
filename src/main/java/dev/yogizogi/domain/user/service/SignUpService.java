package dev.yogizogi.domain.user.service;

import dev.yogizogi.domain.user.exception.AlreadyUseAccountException;
import dev.yogizogi.domain.user.exception.AlreadyUseNicknameException;
import dev.yogizogi.domain.user.exception.AlreadyUsePhoneNumberException;
import dev.yogizogi.domain.user.model.dto.request.CreateUserInDto;
import dev.yogizogi.domain.user.model.dto.response.CreateUserOutDto;
import dev.yogizogi.domain.user.model.entity.Authority;
import dev.yogizogi.domain.user.model.entity.User;
import dev.yogizogi.domain.user.repository.UserRepository;
import dev.yogizogi.global.common.code.ErrorCode;
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

    private final UserService userService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public CreateUserOutDto signUp(CreateUserInDto response) {

        if (userService.isUsedAccountName(response.getAccountName())) {
            throw new AlreadyUseAccountException(ErrorCode.ALREADY_USE_ACCOUNT_NAME);
        }

        if (userService.isUsedNickname(response.getNickname())) {
            throw new AlreadyUseNicknameException(ErrorCode.ALREADY_USE_NICKNAME);
        }

        if (userService.isUsePhoneNumber(response.getPhoneNumber())) {
            throw new AlreadyUsePhoneNumberException(ErrorCode.ALREADY_USE_PHONE_NUMBER);
        }

        User newUser =
                CreateUserInDto.toEntity(
                        UuidUtils.createSequentialUUID(),
                        response,
                        passwordEncoder.encode(response.getPassword()));

        newUser.setRoles(Collections.singletonList(Authority.builder().name("ROLE_USER").build()));

        userRepository.save(newUser);
        return CreateUserOutDto.of(newUser.getId(), newUser.getAccountName());

    }

}