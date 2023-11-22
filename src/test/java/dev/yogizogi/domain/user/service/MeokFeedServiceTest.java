package dev.yogizogi.domain.user.service;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

import dev.yogizogi.domain.review.factory.entity.MenuReviewFactory;
import dev.yogizogi.domain.review.factory.entity.ReviewFactory;
import dev.yogizogi.domain.review.model.entity.MenuReview;
import dev.yogizogi.domain.review.model.entity.Review;
import dev.yogizogi.domain.review.repository.MenuReviewRepository;
import dev.yogizogi.domain.review.repository.ReviewRepository;
import dev.yogizogi.domain.user.exception.NotExistUserException;
import dev.yogizogi.domain.user.factory.entity.UserFactory;
import dev.yogizogi.domain.user.model.dto.response.RetrieveMeokFeedOutDto;
import dev.yogizogi.domain.user.model.entity.User;
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
@DisplayName("MeokFeedService 비즈니스 로직 동작 Test")
class MeokFeedServiceTest {

    @InjectMocks
    private MeokFeedService meokFeedService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private MenuReviewRepository menuReviewRepository;

    @Test
    void 먹피드_조회() {

        // given
        User 조회할_유저 = UserFactory.createUserWithProfile();
        Review 작성한_리뷰 = ReviewFactory.createReview();
        List<Review> 작성한_리뷰들 = List.of(작성한_리뷰);
        MenuReview 작성한_메뉴_리뷰 = MenuReviewFactory.creatMenuReview();
        List<MenuReview> 작성한_메뉴_리뷰들 = List.of(작성한_메뉴_리뷰);

        // mocking
        given(userRepository.findByIdAndStatus(eq(조회할_유저.getId()), eq(BaseStatus.ACTIVE)))
                .willReturn(Optional.of(조회할_유저));

        given(reviewRepository.findAllByUser(eq(조회할_유저)))
                .willReturn(Optional.of(작성한_리뷰들));

        given(menuReviewRepository.findAllByReview(eq(작성한_리뷰)))
                .willReturn(Optional.of(작성한_메뉴_리뷰들));

        // when
        RetrieveMeokFeedOutDto 응답 = meokFeedService.retrieveMeokFeed(조회할_유저.getId());

        // then
        Assertions.assertThat(응답.getUserProfile()).isEqualTo(조회할_유저.getProfile());
        Assertions.assertThat(응답.getMenuReviews().size()).isEqualTo(작성한_메뉴_리뷰들.size());
        for (int i = 0; i < 응답.getMenuReviews().size(); i++) {
            Assertions.assertThat(응답.getMenuReviews().get(i).getId())
                    .isEqualTo(작성한_메뉴_리뷰들.get(i).getId());

            Assertions.assertThat(응답.getMenuReviews().get(i).getRecommendationStatus())
                    .isEqualTo(작성한_메뉴_리뷰들.get(i).getRecommendationStatus());

        }
    }

    @Test
    void 먹피드_조회_데이터_없음() {

        // given
        User 조회할_유저 = UserFactory.createUser();
        List<Review> 작성한_리뷰들 = List.of();
        MenuReview 작성한_메뉴_리뷰 = MenuReviewFactory.creatMenuReview();
        List<MenuReview> 작성한_메뉴_리뷰들 = List.of(작성한_메뉴_리뷰);


        // mocking
        given(userRepository.findByIdAndStatus(eq(조회할_유저.getId()), eq(BaseStatus.ACTIVE)))
                .willReturn(Optional.of(조회할_유저));

        given(reviewRepository.findAllByUser(eq(조회할_유저)))
                .willReturn(Optional.of(작성한_리뷰들));

        // when
        RetrieveMeokFeedOutDto 응답 = meokFeedService.retrieveMeokFeed(조회할_유저.getId());

        // then
        Assertions.assertThat(응답).isNull();

    }

    @Test
    void 먹피드_조회_실패_존재하지_않는_유저() {

        // given
        User 조회할_유저 = UserFactory.createUser();
        Review 작성한_리뷰 = ReviewFactory.createReview();
        List<Review> 작성한_리뷰들 = List.of(작성한_리뷰);
        MenuReview 작성한_메뉴_리뷰 = MenuReviewFactory.creatMenuReview();
        List<MenuReview> 작성한_메뉴_리뷰들 = List.of(작성한_메뉴_리뷰);

        // mocking
        given(userRepository.findByIdAndStatus(eq(조회할_유저.getId()), eq(BaseStatus.ACTIVE)))
                .willReturn(Optional.empty());

        // when
        // then
        Assertions.assertThatThrownBy(
                () -> meokFeedService.retrieveMeokFeed(조회할_유저.getId())
        ).isInstanceOf(NotExistUserException.class);

    }


}