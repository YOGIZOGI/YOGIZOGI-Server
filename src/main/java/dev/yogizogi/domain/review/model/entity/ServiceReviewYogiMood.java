package dev.yogizogi.domain.review.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
public class ServiceReviewYogiMood {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "yogi_mood_id")
    private YogiMood yogiMood;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_review_id")
    private ServiceReview serviceReview;

    @Builder
    public ServiceReviewYogiMood(YogiMood yogiMood, ServiceReview serviceReview) {
        this.yogiMood = yogiMood;
        this.serviceReview = serviceReview;
    }

}
