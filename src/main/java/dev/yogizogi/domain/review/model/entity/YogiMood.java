package dev.yogizogi.domain.review.model.entity;

import lombok.Getter;

@Getter
public enum YogiMood {

    // 함께할 사람
    SOLO, WITH_LOVER, WITH_FRIENDS, WITH_PARENT, WITH_CHILD, WITH_COLLEAGUE,

    // 식사의 목적
    LIGHT_MEAL, GOURMET_MEAL, PAIRING_MEAL,

    // 만남의 목적
    BUSiNESS_MEETING, GROUP_MEETING, ANNIVERSARY;

    private String description;

}
