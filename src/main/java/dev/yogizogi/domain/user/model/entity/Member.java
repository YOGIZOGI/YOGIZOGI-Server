package dev.yogizogi.domain.user.model.entity;

import dev.yogizogi.global.common.model.entity.BaseEntity;
import dev.yogizogi.global.util.UuidUtils;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

    @Id
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(nullable = false, unique = true)
    private String accountName;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String nickName;

    @Column(nullable = false, unique = true)
    private String phoneNumber;

    @Builder
    public Member(String accountName, String password, String nickName, String phoneNumber) {
        this.id = UuidUtils.createSequentialUUID();
        this.accountName = accountName;
        this.password = password;
        this.nickName = nickName;
        this.phoneNumber = phoneNumber;
    }


}
