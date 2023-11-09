package dev.yogizogi.domain.restaurant.repository;

import dev.yogizogi.domain.restaurant.model.entity.Restaurant;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<Restaurant, UUID> {

    Optional<Restaurant> findRestaurantByRestaurantDetails_Name(String name);

}
