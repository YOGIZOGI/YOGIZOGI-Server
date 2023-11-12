package dev.yogizogi.domain.review.service;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

import dev.yogizogi.domain.menu.exception.NotExistMenuException;
import dev.yogizogi.domain.menu.factory.entity.MenuFactory;
import dev.yogizogi.domain.menu.factory.fixtures.MenuFixtures;
import dev.yogizogi.domain.menu.model.entity.Menu;
import dev.yogizogi.domain.menu.repository.MenuRepository;
import dev.yogizogi.domain.restaurant.factory.entity.RestaurantFactory;
import dev.yogizogi.domain.review.execption.NoPermissionRestaurantException;
import dev.yogizogi.domain.review.execption.NotExistReviewException;
import dev.yogizogi.domain.review.factory.dto.CreateMenuReviewFactory;
import dev.yogizogi.domain.review.factory.entity.MenuReviewFactory;
import dev.yogizogi.domain.review.factory.entity.ReviewFactory;
import dev.yogizogi.domain.review.factory.fixtures.MenuReviewFixtures;
import dev.yogizogi.domain.review.model.dto.request.CreateMenuReviewInDto;
import dev.yogizogi.domain.review.model.dto.response.CreateMenuReviewOutDto;
import dev.yogizogi.domain.review.model.dto.response.GetMenuReviewsOutDto;
import dev.yogizogi.domain.review.model.entity.MenuReview;
import dev.yogizogi.domain.review.model.entity.Review;
import dev.yogizogi.domain.review.repository.MenuReviewRepository;
import dev.yogizogi.domain.review.repository.ReviewImageRepository;
import dev.yogizogi.domain.review.repository.ReviewRepository;
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

    @Test
    void 메뉴_리뷰_생성_실퍠_권한_없는_음식점() {

        // given
        CreateMenuReviewInDto 요청 = CreateMenuReviewFactory.createMenuReviewInDto();
        Menu 작성할_메뉴 = MenuFactory.createMenu();
        Review 작성할_리뷰 = ReviewFactory.createReview();

        // mocking
        ReflectionTestUtils.setField(작성할_리뷰, "restaurant", RestaurantFactory.createDiffrentRestaurant());
        given(reviewRepository.findById(eq(요청.getReviewId())))
                .willReturn(Optional.of(작성할_리뷰));

        ReflectionTestUtils.setField(작성할_메뉴, "id", MenuReviewFixtures.작성할_메뉴);
        given(menuRepository.findById(eq(요청.getMenuId())))
                .willReturn(Optional.of(작성할_메뉴));

        // when
        // then
        Assertions.assertThatThrownBy(
                () -> menuReviewService.createMenuReview(요청.getReviewId(), 요청.getMenuId(), 요청.getContent(), 요청.getRecommend(), 요청.getImageUrl())
        ).isInstanceOf(NoPermissionRestaurantException.class);

    }

    @Test
    void 특정_메뉴에_대한_모든_리뷰_조회() {

        // given
        Long 조회할_메뉴_식별자 = 1L;
        Menu 조회할_메뉴 = MenuFactory.createMenu();
        List<MenuReview> 조회할_메뉴_리뷰 = MenuReviewFactory.creatMenuReviews();

        // mocking
        ReflectionTestUtils.setField(조회할_메뉴, "id", 조회할_메뉴_식별자);
        given(menuRepository.findById(eq(조회할_메뉴_식별자)))
                .willReturn(Optional.of(조회할_메뉴));

        given(menuReviewRepository.findByMenu(eq(조회할_메뉴)))
                .willReturn(Optional.of(조회할_메뉴_리뷰));

        // when
        GetMenuReviewsOutDto 응답 = menuReviewService.getMenuReviews(조회할_메뉴_식별자);

        // then
        Assertions.assertThat(응답.getMenuReviews().size()).isEqualTo(조회할_메뉴_리뷰.size());
        for (int i = 0 ; i < 응답.getMenuReviews().size() ; i++) {
            Assertions.assertThat(응답.getMenuReviews().get(i).getMenuReviewId())
                    .isEqualTo(조회할_메뉴_리뷰.get(i).getId());
        }

    }

    @Test
    void 특정_메뉴에_대한_모든_리뷰_조회_실패_존재하지_않는_메뉴() {

        // given
        Long 조회할_메뉴_식별자 = MenuFixtures.메뉴1_식별자;

        // mocking
        given(menuRepository.findById(eq(조회할_메뉴_식별자)))
                .willReturn(Optional.empty());

        // when
        // then
        Assertions.assertThatThrownBy(
                () -> menuReviewService.getMenuReviews(조회할_메뉴_식별자)
        ).isInstanceOf(NotExistMenuException.class);

    }
//
//    @Test
//    void 메뉴_단일_리뷰_조회() {
//
//        // given
//        Long 조회할_메뉴_리뷰 = MenuReviewFixtures.메뉴_리뷰1_식별자;
//
//        // mocking
//        given(menuReviewRepository.findById(eq(조회할_메뉴_리뷰)))
//                .willReturn(Optional.of(MenuReviewFactory.creatMenuReview(조회할_메뉴_리뷰)));
//
//        // when
//        GetMenuReviewOutDto 응답 = menuReviewService.getMenuReview(조회할_메뉴_리뷰);
//
//        // then
//        Assertions.assertThat(응답.getMenuReviewId()).isEqualTo(조회할_메뉴_리뷰);
//
//    }
//
//    @Test
//    void 메뉴_단일_리뷰_조회_존재하지_않는_메뉴_리뷰() {
//
//        // given
//        Long 조회할_메뉴_리뷰 = MenuReviewFixtures.메뉴_리뷰1_식별자;
//
//        // mocking
//        given(menuReviewRepository.findById(eq(조회할_메뉴_리뷰)))
//                .willReturn(Optional.empty());
//
//        // when
//        // then
//        Assertions.assertThatThrownBy(
//                () -> menuReviewService.getMenuReview(조회할_메뉴_리뷰)
//        ).isInstanceOf(NotExistMenuReviewException.class);
//
//    }
//

}