package dev.yogizogi.global.common.status;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MessageStatus {

    SUCCESS("SUCCESS"), FAIL("FAIL");

    private final String description;

}
