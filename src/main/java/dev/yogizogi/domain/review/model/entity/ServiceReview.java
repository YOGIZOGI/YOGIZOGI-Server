package dev.yogizogi.domain.review.model.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ServiceReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id", columnDefinition = "BINARY(16)")
    private Review review;

    @Column(columnDefinition = "TEXT")
    private Double rating;

    @OneToMany(mappedBy = "serviceReview", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ServiceReviewYogiMood> yogiMoods;

    @Builder
    public ServiceReview(Review review, Double rating) {
        this.review = review;
        this.rating = rating;
    }

}
