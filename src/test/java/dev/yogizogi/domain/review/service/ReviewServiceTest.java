package dev.yogizogi.domain.review.service;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

import dev.yogizogi.domain.restaurant.exception.NotExistRestaurantException;
import dev.yogizogi.domain.restaurant.factory.entity.RestaurantFactory;
import dev.yogizogi.domain.restaurant.repository.RestaurantRepository;
import dev.yogizogi.domain.review.factory.dto.CreateReviewFactory;
import dev.yogizogi.domain.review.model.dto.request.CreateReviewInDto;
import dev.yogizogi.domain.review.model.dto.response.CreateReviewOutDto;
import dev.yogizogi.domain.review.repository.ReviewRepository;
import dev.yogizogi.domain.user.exception.NotExistUserException;
import dev.yogizogi.domain.user.factory.entity.UserFactory;
import dev.yogizogi.domain.user.repository.UserRepository;
import dev.yogizogi.global.common.status.BaseStatus;
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
@DisplayName("RestaurantReviewService 비즈니스 로직 동작 테스트")
class ReviewServiceTest {

    @InjectMocks
    private ReviewService reviewService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private ReviewRepository reviewRepository;

    @Test
    void 리뷰_생성() {

        // given
        CreateReviewInDto 요청 = CreateReviewFactory.createReviewInDto();

        // mocking
        given(userRepository.findByIdAndStatus(eq(요청.getUserId()), eq(BaseStatus.ACTIVE)))
                .willReturn(Optional.of(UserFactory.createUser()));

        given(restaurantRepository.findById(eq(요청.getRestaurantId())))
                .willReturn(Optional.of(RestaurantFactory.createRestaurant()));

        // when
        CreateReviewOutDto 응답 = reviewService.createReview(요청.getUserId(), 요청.getRestaurantId());

        // then
        Assertions.assertThat(응답.getUserId()).isEqualTo(요청.getUserId());
        Assertions.assertThat(응답.getRestaurantId()).isEqualTo(요청.getRestaurantId());

    }

    @Test
    void 리뷰_생성_실퍠_존재하지_않는_사용자() {

        // given
        CreateReviewInDto 요청 = CreateReviewFactory.createReviewInDto();

        // mocking
        given(userRepository.findByIdAndStatus(eq(요청.getUserId()), eq(BaseStatus.ACTIVE)))
                .willReturn(Optional.empty());


        // when
        // then
        Assertions.assertThatThrownBy(
                () -> reviewService.createReview(요청.getUserId(), 요청.getRestaurantId())
        ).isInstanceOf(NotExistUserException.class);

    }

    @Test
    void 리뷰_생성_실퍠_존재하지_않는_음식점() {


        // given
        CreateReviewInDto 요청 = CreateReviewFactory.createReviewInDto();

        // mocking
        given(userRepository.findByIdAndStatus(eq(요청.getUserId()), eq(BaseStatus.ACTIVE)))
                .willReturn(Optional.of(UserFactory.createUser()));

        given(restaurantRepository.findById(eq(요청.getRestaurantId())))
                .willReturn(Optional.empty());

        // when
        // then
        Assertions.assertThatThrownBy(
                () -> reviewService.createReview(요청.getUserId(), 요청.getRestaurantId())
        ).isInstanceOf(NotExistRestaurantException.class);

    }

}