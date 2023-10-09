package dev.yogizogi.domain.user.factory.entity;

import static dev.yogizogi.domain.user.factory.fixtures.UserFixtures.*;
import static dev.yogizogi.domain.user.factory.fixtures.PasswordFixtures.*;

import dev.yogizogi.domain.user.model.entity.Authority;
import dev.yogizogi.domain.user.model.entity.User;
import java.util.Collections;

public class UserFactory {

    public static User createUser() {

        User user = User.builder()
                .id(식별자)
                .accountName(계정)
                .password(비밀번호)
                .nickname(닉네임)
                .phoneNumber(핸드폰번호)
                .build();

        user.setRoles(Collections.singletonList(
                Authority.builder().name(역할).build()
        ));

        return user;
    }

    public static User createUserPasswordEncrypt() {

        User user = User.builder()
                .id(식별자)
                .accountName(계정)
                .password(암호화_비밀번호)
                .nickname(닉네임)
                .phoneNumber(핸드폰번호)
                .build();

        user.setRoles(Collections.singletonList(
                Authority.builder().name(역할).build()
        ));

        return user;
    }

}
