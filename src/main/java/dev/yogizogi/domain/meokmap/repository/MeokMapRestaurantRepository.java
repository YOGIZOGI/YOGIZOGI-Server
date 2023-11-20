package dev.yogizogi.domain.meokmap.repository;

import dev.yogizogi.domain.meokmap.model.entity.MeokMap;
import dev.yogizogi.domain.meokmap.model.entity.MeokMapRestaurant;
import dev.yogizogi.domain.restaurant.model.entity.Restaurant;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeokMapRestaurantRepository extends JpaRepository<MeokMapRestaurant, Long> {

    Optional<MeokMapRestaurant> findByMeokMapAndRestaurant(MeokMap meokMap, Restaurant restaurant);

    Optional<List<MeokMapRestaurant>> findByMeokMap(MeokMap meokMap);

}
