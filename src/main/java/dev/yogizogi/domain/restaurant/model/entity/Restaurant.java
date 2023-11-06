package dev.yogizogi.domain.restaurant.model.entity;

import dev.yogizogi.domain.menu.model.entity.Menu;
import dev.yogizogi.global.common.model.entity.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Restaurant extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private RestaurantDetails restaurantDetails;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Menu> menus = new ArrayList<>();

    @Builder
    public Restaurant(RestaurantDetails restaurantDetails) {
        this.restaurantDetails = restaurantDetails;
    }

    public void addMenu(Menu menu) {
        menus.add(menu);
        menu.setRestaurant(this);
    }

}
