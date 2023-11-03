package dev.yogizogi.domain.restaurant.repository;

import dev.yogizogi.domain.restaurant.model.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
}
