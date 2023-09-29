package dev.yogizogi.global.common.status;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum VerificationStatus {

    PASS("PASS"), NOT_PASS("NOT_PASS");


    private final String description;

}
