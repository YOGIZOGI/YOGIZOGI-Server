package dev.yogizogi.domain.review.model.entity;

import dev.yogizogi.domain.menu.model.entity.Menu;
import dev.yogizogi.global.common.model.entity.BaseEntity;
import dev.yogizogi.global.common.status.RecommendationStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
public class MenuReview extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id")
    private Menu menu;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id", columnDefinition = "BINARY(16)")
    private Review review;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Enumerated(EnumType.STRING)
    private RecommendationStatus recommendationStatus;

    @OneToMany(mappedBy = "menuReview", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MenuReviewImage> menuReviewImages = new ArrayList<>();

    @Builder
    public MenuReview(Menu menu, Review review, String content, RecommendationStatus recommendationStatus) {
        this.menu = menu;
        this.review = review;
        this.content = content;
        this.recommendationStatus = recommendationStatus;
    }

}
