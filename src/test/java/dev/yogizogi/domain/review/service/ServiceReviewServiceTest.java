package dev.yogizogi.domain.review.service;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

import dev.yogizogi.domain.review.exception.InValidYogiMoodException;
import dev.yogizogi.domain.review.exception.UnauthorizedReviewException;
import dev.yogizogi.domain.review.factory.entity.ReviewFactory;
import dev.yogizogi.domain.review.factory.fixtures.ServiceReviewFixtures;
import dev.yogizogi.domain.review.model.dto.response.CreateServiceReviewOutDto;
import dev.yogizogi.domain.review.model.entity.Review;
import dev.yogizogi.domain.review.repository.ReviewRepository;
import dev.yogizogi.domain.review.repository.ServiceReviewRepository;
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
@DisplayName("ServiceReviewService 비즈니스 로직 동작 테스트")
class ServiceReviewServiceTest {

    @InjectMocks
    private ServiceReviewService serviceReviewService;

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private ServiceReviewRepository serviceReviewRepository;

    @Test
    void 서비스_리뷰_작성() {

        // given
        Review 서비스_리뷰를_관리하는_리뷰 = ReviewFactory.createReview();
        Double 등록할_평점 = ServiceReviewFixtures.평점;
        List<String> 등록할_요기무드 = ServiceReviewFixtures.요기무드;

        // mocking
        given(reviewRepository.findById(eq(서비스_리뷰를_관리하는_리뷰.getId())))
                .willReturn(Optional.of(ReviewFactory.createReview()));

        // when
        CreateServiceReviewOutDto 응답 = serviceReviewService
                .createServiceReview(서비스_리뷰를_관리하는_리뷰.getId(), 등록할_평점, 등록할_요기무드);

        // then
        Assertions.assertThat(응답.getReviewId()).isEqualTo(서비스_리뷰를_관리하는_리뷰.getId());

    }

    @Test
    void 서비스_리뷰_작성_실패_권한없는_리뷰() {

        // given
        Review 서비스_리뷰를_관리하는_리뷰 = ReviewFactory.createReview();
        Double 등록할_평점 = ServiceReviewFixtures.평점;
        List<String> 등록할_요기무드 = ServiceReviewFixtures.요기무드;

        // mocking
        given(reviewRepository.findById(eq(서비스_리뷰를_관리하는_리뷰.getId())))
                .willReturn(Optional.empty());

        // when
        // then
        Assertions.assertThatThrownBy(
                () ->  serviceReviewService.createServiceReview(서비스_리뷰를_관리하는_리뷰.getId(), 등록할_평점, 등록할_요기무드)
        ).isInstanceOf(UnauthorizedReviewException.class);

    }

    @Test
    void 서비스_리뷰_작성_실패_유효하지_않은_요기무드() {

        // given
        Review 서비스_리뷰를_관리하는_리뷰 = ReviewFactory.createReview();
        Double 등록할_평점 = ServiceReviewFixtures.평점;
        List<String> 등록할_요기무드 = ServiceReviewFixtures.유효하지_않은_요기무드;

        // mocking
        given(reviewRepository.findById(eq(서비스_리뷰를_관리하는_리뷰.getId())))
                .willReturn(Optional.of(ReviewFactory.createReview()));


        // when
        // then
        Assertions.assertThatThrownBy(
                () ->  serviceReviewService.createServiceReview(서비스_리뷰를_관리하는_리뷰.getId(), 등록할_평점, 등록할_요기무드)
        ).isInstanceOf(InValidYogiMoodException.class);

    }

}