package dev.yogizogi.domain.review.repository;

import dev.yogizogi.domain.review.model.entity.YogiMood;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface YogiMoodRepository extends JpaRepository<YogiMood, Long> {

    Optional<YogiMood> findByName(String name);

}
