package dev.yogizogi.domain.review.repository;

import dev.yogizogi.domain.review.model.entity.MenuReview;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuReviewRepository extends JpaRepository<MenuReview, UUID> {
}
