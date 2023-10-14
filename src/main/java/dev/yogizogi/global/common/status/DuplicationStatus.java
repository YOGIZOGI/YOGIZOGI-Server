package dev.yogizogi.global.common.status;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DuplicationStatus {

    EXIST("EXIST"), NOT_EXIST("NOT_EXIST");

    private final String description;

}
