package dev.yogizogi.domain.map.repository;

import dev.yogizogi.domain.map.model.entity.MeokMap;
import dev.yogizogi.domain.restaurant.model.entity.Restaurant;
import dev.yogizogi.domain.user.model.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeokMapRepository extends JpaRepository<MeokMap, Long> {

    Optional<List<MeokMap>> findByUser(User user);

    Optional<MeokMap> findByUserAndRestaurant(User user, Restaurant restaurant);

}
