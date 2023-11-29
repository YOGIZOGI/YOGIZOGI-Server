package dev.yogizogi.domain.review.model.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


//    // 함께할 사람
//    SOLO, WITH_LOVER, WITH_FRIENDS, WITH_PARENT, WITH_CHILD, WITH_COLLEAGUE,
//
//    // 식사의 목적
//    LIGHT_MEAL, GOURMET_MEAL, PAIRING_MEAL,
//
//    // 만남의 목적
//    BUSiNESS_MEETING, GROUP_MEETING, ANNIVERSARY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class YogiMood {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "yogiMood", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ServiceReviewYogiMood> serviceReviewYogiMoods;

    @Builder
    public YogiMood(String name) {
        this.name = name;
    }

}
