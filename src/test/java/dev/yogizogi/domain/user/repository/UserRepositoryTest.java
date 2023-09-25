package dev.yogizogi.domain.user.repository;

import static dev.yogizogi.domain.user.factory.fixtures.UserFixtures.역할;

import dev.yogizogi.domain.user.factory.entity.UserFactory;
import dev.yogizogi.domain.user.model.entity.User;
import dev.yogizogi.global.common.status.BaseStatus;
import java.util.List;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;


@DataJpaTest
@ActiveProfiles("test")
@DisplayName("UserRepository JPA 동작 테스트")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @Order(1)
    void 유저_생성() {

        // given
        User user = UserFactory.createUser();

        // when
        User savedUser = userRepository.save(user);

        // then
        Assertions.assertThat(user.getAccountName()).isEqualTo(savedUser.getAccountName());

        Assertions.assertThat(user.getNickname()).isEqualTo(savedUser.getNickname());

        Assertions.assertThat(user.getPassword()).isEqualTo(savedUser.getPassword());

        Assertions.assertThat(user.getPhoneNumber()).isEqualTo(savedUser.getPhoneNumber());

        Assertions.assertThat(user.getRoles())
                .filteredOn(authority -> authority.getName() == 역할)
                .isNotNull();

    }

    @Test
    @Order(2)
    void 모든_유저_조회() {

        // given
        userRepository.save(UserFactory.createUser());

        // when
        List<User> users = userRepository.findAll();

        // then
        Assertions.assertThat(users).isNotNull();
        Assertions.assertThat(users.size()).isEqualTo(1);

    }

    @Test
    @Order(3)
    void 단일_유저_조회() {

        // given
        User user = userRepository.save(UserFactory.createUser());

        // when
        Optional<User> users = userRepository.findByAccountNameAndStatus(user.getAccountName(), BaseStatus.ACTIVE);

        // then
        Assertions.assertThat(user).isEqualTo(users.get());

    }

    @Test
    @Order(4)
    void 유저_삭제() {

        // given
        User user = userRepository.save(UserFactory.createUser());
        user.inactive();

        // when
        User deleteUser = userRepository.save(user);

        // then
        Assertions.assertThat(deleteUser.getStatus()).isNotEqualTo(BaseStatus.ACTIVE);

    }



}