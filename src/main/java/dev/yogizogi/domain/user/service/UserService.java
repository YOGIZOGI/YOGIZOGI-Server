package dev.yogizogi.domain.user.service;

import static dev.yogizogi.global.common.model.constant.Format.DONE;

import dev.yogizogi.domain.user.exception.AlreadyUsePasswordException;
import dev.yogizogi.domain.user.exception.NotExistPhoneNumberException;
import dev.yogizogi.domain.user.model.dto.response.DeleteUserOutDto;
import dev.yogizogi.domain.user.model.dto.response.FindPasswordOutDto;
import dev.yogizogi.domain.user.model.entity.User;
import dev.yogizogi.domain.user.repository.UserRepository;
import dev.yogizogi.global.common.code.ErrorCode;
import dev.yogizogi.global.common.status.BaseStatus;
import dev.yogizogi.global.common.status.MessageStatus;
import dev.yogizogi.infra.coolsms.CoolSmsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final CoolSmsService coolSmsService;

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public boolean isUsedNickname(String nickname) {
        return userRepository.findByNicknameAndStatus(nickname, BaseStatus.ACTIVE).isPresent();
    }

    @Transactional(readOnly = true)
    public boolean isUsedPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumberAndStatus(phoneNumber, BaseStatus.ACTIVE).isPresent();
    }

    @Transactional(readOnly = true)
    public FindPasswordOutDto findPassword(String phoneNumber) {

        User findUser = userRepository.findByPhoneNumberAndStatus(phoneNumber, BaseStatus.ACTIVE)
                .orElseThrow(() -> new NotExistPhoneNumberException(ErrorCode.NOT_EXIST_PHONE_NUMBER));

        coolSmsService.sendOne(phoneNumber);

        return FindPasswordOutDto.of(
                MessageStatus.SUCCESS, findUser.getPhoneNumber()
        );

    }

    public String updatePassword(String phoneNumber, String password) {

        User findUser = userRepository.findByPhoneNumberAndStatus(phoneNumber, BaseStatus.ACTIVE)
                .orElseThrow(() -> new NotExistPhoneNumberException(ErrorCode.NOT_EXIST_PHONE_NUMBER));

        if (passwordEncoder.matches(password, findUser.getPassword())) {
            throw new AlreadyUsePasswordException(ErrorCode.ALREADY_USE_PASSWORD);
        }

        findUser.setPassword(passwordEncoder.encode(password));
        userRepository.save(findUser);

        return DONE;

    }

    public DeleteUserOutDto deleteUser(String phoneNumber) throws NotExistPhoneNumberException {

        User deleteUser = userRepository.findByPhoneNumberAndStatus(phoneNumber, BaseStatus.ACTIVE)
                .orElseThrow(() -> new NotExistPhoneNumberException(ErrorCode.NOT_EXIST_PHONE_NUMBER));

        deleteUser.inactive();
        userRepository.save(deleteUser);

        return DeleteUserOutDto.of(
                deleteUser.getPhoneNumber(),
                deleteUser.getStatus()
        );

    }


}
