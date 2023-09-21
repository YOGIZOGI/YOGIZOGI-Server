package dev.yogizogi.domain.member.repository;

import dev.yogizogi.domain.member.model.entity.User;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByIdAndAccountName(UUID  id, String accountName);
    Optional<User> findByAccountName(String accountName);

    Optional<User> findByNickname(String nickname);

    Optional<User> findByPhoneNumber(String phoneNumber);

}
