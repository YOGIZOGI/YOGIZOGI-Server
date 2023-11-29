package dev.yogizogi.domain.review.repository;

import dev.yogizogi.domain.review.model.entity.ServiceReviewYogiMood;
import dev.yogizogi.domain.review.model.entity.YogiMood;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceReviewYogiMoodRepository extends JpaRepository<ServiceReviewYogiMood, Long> {

    Optional<List<ServiceReviewYogiMood>> findAllByYogiMoodIn(List<YogiMood> yogiMood);

}
