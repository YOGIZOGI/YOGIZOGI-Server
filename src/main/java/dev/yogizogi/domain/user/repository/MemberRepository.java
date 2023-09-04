package dev.yogizogi.domain.user.repository;

import dev.yogizogi.domain.user.model.entity.Member;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, UUID> {

    Optional<Member> findByAccountName(String accountName);

    Optional<Member> findByNickName(String nickName);

    Optional<Member> findByPhoneNumber(String phoneNumber);

}
