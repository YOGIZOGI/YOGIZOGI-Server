package dev.yogizogi.domain.meokprofile.service;

import static dev.yogizogi.domain.meokprofile.factory.fixtures.MeokProfileFixtures.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

import dev.yogizogi.domain.meokprofile.exception.InvalidTagException;
import dev.yogizogi.domain.meokprofile.model.dto.response.CreateMeokProfileOutDto;
import dev.yogizogi.domain.meokprofile.repository.MeokProfileRepository;
import dev.yogizogi.domain.user.exception.NotExistUserException;
import dev.yogizogi.domain.user.factory.entity.UserFactory;
import dev.yogizogi.domain.user.repository.UserRepository;
import dev.yogizogi.global.common.status.BaseStatus;

import java.util.List;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
@DisplayName("MeokProfileService 비즈니스 로직 동작 테스트")
class MeokProfileServiceTest {

    @InjectMocks
    private MeokProfileService meokProfileService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private MeokProfileRepository meokProfileRepository;

    @Test
    void 먹프로필_생성() {

        // given
        // mocking
        given(userRepository.findByIdAndStatus(eq(등록할_식별자), eq(BaseStatus.ACTIVE)))
                .willReturn(Optional.of(UserFactory.createUserWithProfile()));

        // when
        CreateMeokProfileOutDto req = meokProfileService
                .createMeokProfile(등록할_식별자, 먹태그, 매운맛_선호도, 짠맛_선호도, 단맛_선호도);

        // then
        Assertions.assertThat(req.getPreference().getSpicyPreference()).isEqualTo(매운맛_선호도);
        Assertions.assertThat(req.getPreference().getSaltyPreference()).isEqualTo(짠맛_선호도);
        Assertions.assertThat(req.getPreference().getSweetnessPreference()).isEqualTo(단맛_선호도);

    }

    @Test
    void 먹프로필_생성_실패_존재하지_않는_유저() {

        // given
        // mocking
        given(userRepository.findByIdAndStatus(eq(등록할_식별자), eq(BaseStatus.ACTIVE))).willReturn(Optional.empty());

        // when
        // then
        Assertions.assertThatThrownBy(
                () -> meokProfileService
                        .createMeokProfile(등록할_식별자, 먹태그, 매운맛_선호도, 짠맛_선호도, 단맛_선호도)
                )
                .isInstanceOf(NotExistUserException.class);

    }

    @Test
    void 먹프로필_생성_실패_유효하지_않은_먹태그() {

        // given
        // mocking
        given(userRepository.findByIdAndStatus(eq(등록할_식별자), eq(BaseStatus.ACTIVE)))
            .willReturn(Optional.of(UserFactory.createUserWithProfile()));

        // when
        // then
        Assertions.assertThatThrownBy(
                () -> meokProfileService
                    .createMeokProfile(등록할_식별자, List.of("없음"), 매운맛_선호도, 짠맛_선호도, 단맛_선호도)
            )
            .isInstanceOf(InvalidTagException.class);

    }

}