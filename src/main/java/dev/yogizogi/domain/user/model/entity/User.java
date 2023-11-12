package dev.yogizogi.domain.user.model.entity;

import dev.yogizogi.domain.meokprofile.model.entity.MeokProfile;
import dev.yogizogi.global.common.model.entity.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

    @Id
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(nullable = false, unique = true)
    private String phoneNumber;

    @Column(nullable = false)
    private String password;

    @Embedded
    private Profile profile;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private FirstLoginStatus firstLoginStatus;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private MeokProfile meokProfile;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Authority> roles = new ArrayList<>();

    public void setRoles(List<Authority> roles) {
        this.roles = roles;
        roles.forEach(role -> role.setUser(this));
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFirstLoginStatus(FirstLoginStatus firstLoginStatus) {
        this.firstLoginStatus = firstLoginStatus;
    }

    public void setProfile(String nickname, String imageUrl, String introduction) {

        this.profile = Profile.builder()
                .nickname(nickname)
                .imageUrl(imageUrl)
                .introduction(introduction)
                .build();

    }

    @Builder
    public User(UUID id, String phoneNumber, String password, FirstLoginStatus firstLoginStatus) {
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.firstLoginStatus = FirstLoginStatus.ACTIVE;
    }

}
