package dev.yogizogi.domain.review.factory.entity;

import dev.yogizogi.domain.review.model.entity.YogiMood;

public class YogiMoodFactory {

    public static YogiMood createYogiMood(String name) {
        return YogiMood.builder()
                .name(name)
                .build();
    }

}
