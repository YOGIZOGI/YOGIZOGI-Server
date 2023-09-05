package dev.yogizogi.domain.user.service;

import dev.yogizogi.domain.user.exception.MemberException;
import dev.yogizogi.domain.user.model.dto.request.CreateMemberInDto;
import dev.yogizogi.domain.user.model.dto.response.CreateMemberOutDto;
import dev.yogizogi.domain.user.model.entity.Member;
import dev.yogizogi.domain.user.repository.MemberRepository;
import dev.yogizogi.global.common.code.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class MemberService {

    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    public CreateMemberOutDto signUp(CreateMemberInDto response) {

        isDuplicate(response.getAccountName(), response.getNickName(), response.getPhoneNumber());
        Member newMember = CreateMemberInDto.toEntity(response, passwordEncoder.encode(response.getPassword()));
        memberRepository.save(newMember);

        return CreateMemberOutDto.of(newMember);

    }

    private void isDuplicate(String accountName, String nickName, String phoneNumber) {

        if (!memberRepository.findByAccountName(accountName).isEmpty()) {
            throw new MemberException(ErrorCode.DUPLICATE_MEMBER_INFORMATION);
        }

        if (!memberRepository.findByNickName(nickName).isEmpty()) {
            throw new MemberException(ErrorCode.DUPLICATE_MEMBER_INFORMATION);
        }

        if (!memberRepository.findByPhoneNumber(phoneNumber).isEmpty()) {
            throw new MemberException(ErrorCode.DUPLICATE_MEMBER_INFORMATION);
        }

    }



}
