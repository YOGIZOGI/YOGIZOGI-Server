package dev.yogizogi.domain.review.repository;

import dev.yogizogi.domain.review.model.entity.ServiceReview;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceReviewRepository extends JpaRepository<ServiceReview, Long> {

}
