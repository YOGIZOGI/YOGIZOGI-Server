package dev.yogizogi.domain.review.service;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

import dev.yogizogi.domain.menu.exception.NotExistMenuException;
import dev.yogizogi.domain.menu.factory.entity.MenuFactory;
import dev.yogizogi.domain.menu.model.entity.Menu;
import dev.yogizogi.domain.menu.repository.MenuRepository;
import dev.yogizogi.domain.review.execption.NotExistReviewException;
import dev.yogizogi.domain.review.factory.dto.CreateMenuReviewFactory;
import dev.yogizogi.domain.review.factory.entity.ReviewFactory;
import dev.yogizogi.domain.review.factory.fixtures.MenuReviewFixtures;
import dev.yogizogi.domain.review.model.dto.request.CreateMenuReviewInDto;
import dev.yogizogi.domain.review.model.dto.response.CreateMenuReviewOutDto;
import dev.yogizogi.domain.review.repository.MenuReviewRepository;
import dev.yogizogi.domain.review.repository.ReviewImageRepository;
import dev.yogizogi.domain.review.repository.ReviewRepository;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.util.ReflectionTestUtils;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
@DisplayName("MenuReviewService 비즈니스 로직 동작 테스트")
class MenuReviewServiceTest {

    @InjectMocks
    private MenuReviewService menuReviewService;

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private MenuRepository menuRepository;

    @Mock
    private MenuReviewRepository menuReviewRepository;

    @Mock
    private ReviewImageRepository reviewImageRepository;


    @Test
    void 메뉴_리뷰_생성() {

        // given
        CreateMenuReviewInDto 요청 = CreateMenuReviewFactory.createMenuReviewInDto();
        Menu 작성할_메뉴 = MenuFactory.createMenu();


        // mocking
        given(reviewRepository.findById(eq(요청.getReviewId())))
                .willReturn(Optional.of(ReviewFactory.createReview()));

        ReflectionTestUtils.setField(작성할_메뉴, "id", MenuReviewFixtures.작성할_메뉴);
        given(menuRepository.findById(eq(요청.getMenuId())))
                .willReturn(Optional.of(작성할_메뉴));



        // when
        CreateMenuReviewOutDto 응답 = menuReviewService.createMenuReview(
                요청.getReviewId(), 요청.getMenuId(), 요청.getContent(), 요청.getRecommend(), 요청.getImageUrl()
        );

        // then
        Assertions.assertThat(응답.getMenuId()).isNotNull();
        Assertions.assertThat(응답.getReviewId()).isEqualTo(요청.getReviewId());
        Assertions.assertThat(응답.getMenuId()).isEqualTo(요청.getMenuId());

    }

    @Test
    void 메뉴_리뷰_생성_실퍠_존재하지_않는_리뷰() {

        // given
        CreateMenuReviewInDto 요청 = CreateMenuReviewFactory.createMenuReviewInDto();

        // mocking
        given(reviewRepository.findById(eq(요청.getReviewId())))
                .willReturn(Optional.empty());;

        // when
        // then
        Assertions.assertThatThrownBy(
                () -> menuReviewService.createMenuReview(요청.getReviewId(), 요청.getMenuId(), 요청.getContent(), 요청.getRecommend(), 요청.getImageUrl())
        ).isInstanceOf(NotExistReviewException.class);

    }

    @Test
    void 메뉴_리뷰_생성_실퍠_존재하지_않는_메뉴() {


        // given
        CreateMenuReviewInDto 요청 = CreateMenuReviewFactory.createMenuReviewInDto();

        // mocking
        given(reviewRepository.findById(eq(요청.getReviewId())))
                .willReturn(Optional.of(ReviewFactory.createReview()));

        given(menuRepository.findById(eq(요청.getMenuId())))
                .willReturn(Optional.empty());

        // when
        // then
        Assertions.assertThatThrownBy(
                () -> menuReviewService.createMenuReview(요청.getReviewId(), 요청.getMenuId(), 요청.getContent(), 요청.getRecommend(), 요청.getImageUrl())
        ).isInstanceOf(NotExistMenuException.class);

    }

}