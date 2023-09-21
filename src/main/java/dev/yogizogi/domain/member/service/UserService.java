package dev.yogizogi.domain.member.service;

import dev.yogizogi.domain.member.exception.UserException;
import dev.yogizogi.domain.member.model.dto.request.CreateUserInDto;
import dev.yogizogi.domain.member.model.dto.response.CreateUserOutDto;
import dev.yogizogi.domain.member.model.entity.User;
import dev.yogizogi.domain.member.repository.UserRepository;
import dev.yogizogi.global.common.code.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public CreateUserOutDto signUp(CreateUserInDto response) {

        if (checkAccountNameDuplication(response.getAccountName())) {
            throw new UserException(ErrorCode.DUPLICATE_ACCOUNT_NAME);
        }

        if (checkNicknameDuplication(response.getNickname())) {
            throw new UserException(ErrorCode.DUPLICATE_NICKNAME);
        }

        if (checkPhoneNumberDuplication(response.getPhoneNumber())) {
            throw new UserException(ErrorCode.DUPLICATE_PHONE_NUMBER);
        }

        User newUser = CreateUserInDto.toEntity(response, passwordEncoder.encode(response.getPassword()));
        userRepository.save(newUser);

        return CreateUserOutDto.of(newUser);

    }
    public boolean checkAccountNameDuplication(String accountName) {
        return userRepository.findByAccountName(accountName).isPresent();
    }

    public boolean checkNicknameDuplication(String nickname) {
        return userRepository.findByNickname(nickname).isPresent();
    }

    public boolean checkPhoneNumberDuplication(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber).isPresent();
    }

}
