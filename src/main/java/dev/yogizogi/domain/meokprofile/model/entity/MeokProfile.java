package dev.yogizogi.domain.meokprofile.model.entity;

import dev.yogizogi.domain.user.model.entity.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
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
    private Preference preference;

    @OneToOne(mappedBy = "meokProfile", cascade = CascadeType.ALL, orphanRemoval = true)
    private User user;

    @Builder
    public MeokProfile(Preference preference) {
        this.preference = preference;
    }

}

