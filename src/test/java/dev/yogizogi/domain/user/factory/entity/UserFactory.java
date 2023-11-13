package dev.yogizogi.domain.user.factory.entity;

import static dev.yogizogi.domain.user.factory.fixtures.PasswordFixtures.비밀번호;
import static dev.yogizogi.domain.user.factory.fixtures.PasswordFixtures.암호화_비밀번호;
import static dev.yogizogi.domain.user.factory.fixtures.UserFixtures.식별자;
import static dev.yogizogi.domain.user.factory.fixtures.UserFixtures.역할;
import static dev.yogizogi.domain.user.factory.fixtures.UserFixtures.처음_로그인;
import static dev.yogizogi.domain.user.factory.fixtures.UserFixtures.핸드폰_번호;

import dev.yogizogi.domain.meokprofile.factory.entity.MeokProfileFactory;
import dev.yogizogi.domain.user.model.entity.Authority;
import dev.yogizogi.domain.user.model.entity.User;
import java.util.Collections;
import org.springframework.test.util.ReflectionTestUtils;

public class UserFactory {

    public static User createUser() {

        User user = User.builder()
                .id(식별자)
                .phoneNumber(핸드폰_번호)
                .password(비밀번호)
                .firstLoginStatus(처음_로그인)
                .build();

        ReflectionTestUtils.setField(user, "profile", ProfileFactory.createProfile());
        ReflectionTestUtils.setField(user, "meokProfile", MeokProfileFactory.createMeokProfileWithUser(user));

        user.setRoles(Collections.singletonList(
                Authority.builder().name(역할).build()
        ));

        return user;

    }

    public static User createUserWithProfile() {

        User user = User.builder()
                .id(식별자)
                .phoneNumber(핸드폰_번호)
                .password(비밀번호)
                .firstLoginStatus(처음_로그인)
                .build();

        user.setRoles(Collections.singletonList(
                Authority.builder().name(역할).build()
        ));

        ReflectionTestUtils.setField(user, "profile", ProfileFactory.createProfile());
        ReflectionTestUtils.setField(user, "meokProfile", MeokProfileFactory.createMeokProfile());

        return user;
    }

    public static User createUserPasswordEncrypt() {

        User user = User.builder()
                .id(식별자)
                .phoneNumber(핸드폰_번호)
                .password(암호화_비밀번호)
                .firstLoginStatus(처음_로그인)
                .build();

        user.setRoles(Collections.singletonList(
                Authority.builder().name(역할).build()
        ));

        return user;
    }

}
