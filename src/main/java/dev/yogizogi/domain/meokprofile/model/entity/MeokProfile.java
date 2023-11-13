package dev.yogizogi.domain.meokprofile.model.entity;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MeokProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Intensity intensity;

    @Embedded
    private Preference preference;


    @Builder
    public MeokProfile(Intensity intensity, Preference preference) {
        this.intensity = intensity;
        this.preference = preference;
    }

}

