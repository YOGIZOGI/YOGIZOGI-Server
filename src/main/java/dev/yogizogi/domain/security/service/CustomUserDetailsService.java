package dev.yogizogi.domain.security.service;

import dev.yogizogi.domain.member.exception.NotExistAccountException;
import dev.yogizogi.domain.member.model.entity.User;
import dev.yogizogi.domain.member.repository.UserRepository;
import dev.yogizogi.domain.security.model.CustomUserDetails;
import dev.yogizogi.global.common.code.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public CustomUserDetails loadUserByUsername(String accountName) {

        User user = userRepository.findByAccountName(accountName)
                .orElseThrow(() -> new NotExistAccountException(ErrorCode.NOT_EXIST_ACCOUNT));

        return CustomUserDetails.of(user);
    }

}
