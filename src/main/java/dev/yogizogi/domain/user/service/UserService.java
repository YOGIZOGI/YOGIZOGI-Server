package dev.yogizogi.domain.user.service;

import static dev.yogizogi.global.common.model.constant.Format.DONE;

import dev.yogizogi.domain.user.exception.AlreadyUsePasswordException;
import dev.yogizogi.domain.user.exception.NotExistAccountException;
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
    public boolean isUsedAccountName(String accountName) {
        return userRepository.findByAccountNameAndStatus(accountName, BaseStatus.ACTIVE).isPresent();
    }

    @Transactional(readOnly = true)
    public boolean isUsedNickname(String nickname) {
        return userRepository.findByNicknameAndStatus(nickname, BaseStatus.ACTIVE).isPresent();
    }

    @Transactional(readOnly = true)
    public boolean isUsePhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumberAndStatus(phoneNumber, BaseStatus.ACTIVE).isPresent();
    }

    @Transactional(readOnly = true)
    public FindPasswordOutDto findPassword(String phoneNumber) {

        User findUser = userRepository.findByPhoneNumberAndStatus(phoneNumber, BaseStatus.ACTIVE)
                .orElseThrow(() -> new NotExistAccountException(ErrorCode.NOT_EXIST_ACCOUNT));

        coolSmsService.sendOne(phoneNumber);

        return FindPasswordOutDto.of(
                MessageStatus.SUCCESS, findUser.getPhoneNumber()
        );

    }

    public String updatePassword(String phoneNumber, String password) {

        User findUser = userRepository.findByPhoneNumberAndStatus(phoneNumber, BaseStatus.ACTIVE)
                .orElseThrow(() -> new NotExistAccountException(ErrorCode.NOT_EXIST_ACCOUNT));

        if (passwordEncoder.matches(password, findUser.getPassword())) {
            throw new AlreadyUsePasswordException(ErrorCode.ALREADY_USE_PASSWORD);
        }

        findUser.setPassword(passwordEncoder.encode(password));
        userRepository.save(findUser);

        return DONE;

    }

    public DeleteUserOutDto deleteUser(String accountName) throws NotExistAccountException {

        User deleteUser = userRepository.findByAccountNameAndStatus(accountName, BaseStatus.ACTIVE)
                .orElseThrow(() -> new NotExistAccountException(ErrorCode.NOT_EXIST_ACCOUNT));

        deleteUser.inactive();
        userRepository.save(deleteUser);

        return DeleteUserOutDto.of(
                deleteUser.getAccountName(),
                deleteUser.getStatus()
        );

    }


}
