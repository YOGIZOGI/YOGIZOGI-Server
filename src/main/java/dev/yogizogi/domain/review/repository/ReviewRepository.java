package dev.yogizogi.domain.review.repository;

import dev.yogizogi.domain.review.model.entity.Review;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, UUID> {
}
