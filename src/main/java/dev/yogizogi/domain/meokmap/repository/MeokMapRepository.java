package dev.yogizogi.domain.meokmap.repository;

import dev.yogizogi.domain.meokmap.model.entity.MeokMap;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeokMapRepository extends JpaRepository<MeokMap, Long> {

    Optional<MeokMap> findByUserId(UUID userId);

}
