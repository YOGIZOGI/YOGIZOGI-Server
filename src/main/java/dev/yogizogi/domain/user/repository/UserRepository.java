package dev.yogizogi.domain.user.repository;

import dev.yogizogi.domain.user.model.entity.User;
import dev.yogizogi.global.common.status.BaseStatus;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByIdAndPhoneNumberAndStatus(UUID  id, String phoneNumber, BaseStatus status);

    Optional<User> findByNicknameAndStatus(String nickname,  BaseStatus status);

    Optional<User> findByPhoneNumberAndStatus(String phoneNumber, BaseStatus status);

}
