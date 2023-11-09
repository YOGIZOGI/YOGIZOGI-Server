package dev.yogizogi.global.common.status;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RecommendationStatus {

    RECOMMEND("추천"), NOT_RECOMMEND("비추천");

    private final String description;

}
