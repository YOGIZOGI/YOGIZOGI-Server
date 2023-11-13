package dev.yogizogi.domain.review.repository;

import dev.yogizogi.domain.review.model.entity.MenuReviewImage;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuReviewImageRepository extends JpaRepository<MenuReviewImage, UUID> {
}
