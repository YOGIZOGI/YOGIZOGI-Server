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

        checkDuplication(response.getAccountName(), response.getNickname(), response.getPhoneNumber());
        User newUser = CreateUserInDto.toEntity(response, passwordEncoder.encode(response.getPassword()));
        userRepository.save(newUser);

        return CreateUserOutDto.of(newUser);

    }

    private void checkDuplication(String accountName, String nickName, String phoneNumber) {

        if (!userRepository.findByAccountName(accountName).isEmpty()) {
            throw new UserException(ErrorCode.DUPLICATE_ACCOUNT_NAME);
        }

        if (!userRepository.findByNickname(nickName).isEmpty()) {
            throw new UserException(ErrorCode.DUPLICATE_NICKNAME);
        }

        if (!userRepository.findByPhoneNumber(phoneNumber).isEmpty()) {
            throw new UserException(ErrorCode.DUPLICATE_PHONE_NUMBER);
        }

    }



}
