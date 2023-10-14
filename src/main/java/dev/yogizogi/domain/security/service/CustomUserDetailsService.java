package dev.yogizogi.domain.security.service;

import dev.yogizogi.domain.user.exception.NotExistPhoneNumberException;
import dev.yogizogi.domain.user.model.entity.User;
import dev.yogizogi.domain.user.repository.UserRepository;
import dev.yogizogi.domain.security.model.CustomUserDetails;
import dev.yogizogi.global.common.code.ErrorCode;
import dev.yogizogi.global.common.status.BaseStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public CustomUserDetails loadUserByUsername(String phoneNumber) {

        User user = userRepository.findByPhoneNumberAndStatus(phoneNumber, BaseStatus.ACTIVE)
                .orElseThrow(() -> new NotExistPhoneNumberException(ErrorCode.NOT_EXIST_PHONE_NUMBER));

        return CustomUserDetails.of(user);
    }

}
