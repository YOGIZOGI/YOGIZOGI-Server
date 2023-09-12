package dev.yogizogi.domain.security.service;

import dev.yogizogi.domain.member.exception.NotExistAccountException;
import dev.yogizogi.domain.member.model.entity.Member;
import dev.yogizogi.domain.member.repository.MemberRepository;
import dev.yogizogi.domain.security.model.CustomUserDetails;
import dev.yogizogi.global.common.code.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public CustomUserDetails loadUserByUsername(String accountName) {

        Member member = memberRepository.findByAccountName(accountName)
                .orElseThrow(() -> new NotExistAccountException(ErrorCode.NOT_EXIST_ACCOUNT));

        return CustomUserDetails.of(member);
    }

}
