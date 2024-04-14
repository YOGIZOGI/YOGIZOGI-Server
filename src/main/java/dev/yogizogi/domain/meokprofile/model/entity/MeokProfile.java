package dev.yogizogi.domain.meokprofile.model.entity;

import java.util.List;

import dev.yogizogi.domain.user.model.entity.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

    @Enumerated(EnumType.STRING)
    private List<Tag> tags;

    @Embedded
    private Preference preference;

    @OneToOne(mappedBy = "meokProfile", cascade = CascadeType.ALL, orphanRemoval = true)
    private User user;

    @Builder
    public MeokProfile(List<Tag> tags, Preference preference) {
        this.tags = tags;
        this.preference = preference;
    }

}

