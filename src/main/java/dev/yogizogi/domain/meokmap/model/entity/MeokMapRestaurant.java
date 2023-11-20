package dev.yogizogi.domain.meokmap.model.entity;

import dev.yogizogi.domain.restaurant.model.entity.Restaurant;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MeokMapRestaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "MeokMap_id")
    private MeokMap meokMap;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @Builder
    public MeokMapRestaurant(MeokMap meokMap, Restaurant restaurant) {
        this.meokMap = meokMap;
        this.restaurant = restaurant;
    }

}
