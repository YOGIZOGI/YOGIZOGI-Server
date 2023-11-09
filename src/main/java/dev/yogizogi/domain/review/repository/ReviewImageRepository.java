package dev.yogizogi.domain.review.repository;

import dev.yogizogi.domain.review.model.entity.ReviewImage;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewImageRepository extends JpaRepository<ReviewImage, UUID> {
}
