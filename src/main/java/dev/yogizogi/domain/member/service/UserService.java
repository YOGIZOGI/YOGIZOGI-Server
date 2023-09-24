package dev.yogizogi.domain.member.service;

import dev.yogizogi.domain.member.exception.NotExistAccountException;
import dev.yogizogi.domain.member.exception.UserException;
import dev.yogizogi.domain.member.model.dto.request.CreateUserInDto;
import dev.yogizogi.domain.member.model.dto.response.CreateUserOutDto;
import dev.yogizogi.domain.member.model.dto.response.DeleteUserOutDto;
import dev.yogizogi.domain.member.model.entity.Authority;
import dev.yogizogi.domain.member.model.entity.User;
import dev.yogizogi.domain.member.repository.UserRepository;
import dev.yogizogi.global.common.code.ErrorCode;
import dev.yogizogi.global.common.status.BaseStatus;
import java.util.Collections;
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
        newUser.setRoles(Collections.singletonList(
                Authority.builder().name("ROLE_USER").build())
        );
        userRepository.save(newUser);

        return CreateUserOutDto.of(newUser);

    }

    public boolean checkAccountNameDuplication(String accountName) {
        return userRepository.findByAccountNameAndStatus(accountName, BaseStatus.ACTIVE).isPresent();
    }

    public boolean checkNicknameDuplication(String nickname) {
        return userRepository.findByNicknameAndStatus(nickname, BaseStatus.ACTIVE).isPresent();
    }

    public boolean checkPhoneNumberDuplication(String phoneNumber) {
        return userRepository.findByPhoneNumberAndStatus(phoneNumber, BaseStatus.ACTIVE).isPresent();
    }


    public DeleteUserOutDto deleteUser(String accountName) throws NotExistAccountException {

        User deleteUser = userRepository.findByAccountNameAndStatus(accountName, BaseStatus.ACTIVE)
                .orElseThrow(() -> new NotExistAccountException(ErrorCode.NOT_EXIST_ACCOUNT));

        deleteUser.inactive();

        return DeleteUserOutDto.of(
                deleteUser.getAccountName()
        );

    }


}
