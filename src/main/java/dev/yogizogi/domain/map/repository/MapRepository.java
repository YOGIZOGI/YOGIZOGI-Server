package dev.yogizogi.domain.map.repository;

import dev.yogizogi.domain.map.model.entity.Map;
import dev.yogizogi.domain.user.model.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MapRepository extends JpaRepository<Map, Long> {

    Optional<Map> findByUser(User user);

}
