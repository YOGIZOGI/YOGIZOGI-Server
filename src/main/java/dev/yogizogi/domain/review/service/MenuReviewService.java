package dev.yogizogi.domain.review.service;

import static dev.yogizogi.global.common.code.ErrorCode.FAIL_TO_REVIEW_UNAUTHORIZED_RESTAURANT;
import static dev.yogizogi.global.common.code.ErrorCode.NOT_EXIST_MENU;
import static dev.yogizogi.global.common.code.ErrorCode.NOT_EXIST_REVIEW;

import dev.yogizogi.domain.menu.exception.NotExistMenuException;
import dev.yogizogi.domain.menu.model.entity.Menu;
import dev.yogizogi.domain.menu.repository.MenuRepository;
import dev.yogizogi.domain.meokprofile.model.entity.MeokProfile;
import dev.yogizogi.domain.meokprofile.model.vo.MeokProfileVO;
import dev.yogizogi.domain.review.exception.NotExistMenuReviewException;
import dev.yogizogi.domain.review.exception.NotExistReviewException;
import dev.yogizogi.domain.review.exception.UnauthorizedRestaurantException;
import dev.yogizogi.domain.review.model.dto.request.CreateMenuReviewInDto;
import dev.yogizogi.domain.review.model.dto.response.CreateMenuReviewOutDto;
import dev.yogizogi.domain.review.model.dto.response.GetMenuReviewOutDto;
import dev.yogizogi.domain.review.model.dto.response.GetMenuReviewsOutDto;
import dev.yogizogi.domain.review.model.entity.MenuReview;
import dev.yogizogi.domain.review.model.entity.MenuReviewImage;
import dev.yogizogi.domain.review.model.entity.Review;
import dev.yogizogi.domain.review.model.vo.MenuReviewVO;
import dev.yogizogi.domain.review.repository.MenuReviewImageRepository;
import dev.yogizogi.domain.review.repository.MenuReviewRepository;
import dev.yogizogi.domain.review.repository.ReviewRepository;
import dev.yogizogi.domain.user.model.entity.User;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MenuReviewService {

    private final ReviewRepository reviewRepository;
    private final MenuRepository menuRepository;
    private final MenuReviewRepository menuReviewRepository;
    private final MenuReviewImageRepository menuReviewImageRepository;

    public CreateMenuReviewOutDto createMenuReview
            (UUID reviewId, Long menuId, String content, String recommend, List<String> imageUrl) {

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new NotExistReviewException(NOT_EXIST_REVIEW));

        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new NotExistMenuException(NOT_EXIST_MENU));

        if (!isAuthorizeRestaurant(review, menu)) {
            throw new UnauthorizedRestaurantException(FAIL_TO_REVIEW_UNAUTHORIZED_RESTAURANT);
        }

        MenuReview menuReview = CreateMenuReviewInDto.toEntity(review, menu, content, recommend);
        menuReviewRepository.save(menuReview);

        List<MenuReviewImage> menuReviewImage = imageUrl.stream()
                .map(url -> MenuReviewImage.builder()
                        .menuReview(menuReview)
                        .url(url)
                        .build())
                .collect(Collectors.toList());

        menuReviewImageRepository.saveAll(menuReviewImage);

        return CreateMenuReviewOutDto.of(menuReview.getId(), menuReview.getReview().getId(),
                menuReview.getMenu().getId());

    }

    private static boolean isAuthorizeRestaurant(Review review, Menu menu) {
        if (menu.getRestaurant() == review.getRestaurant()) {
            return true;
        }
        return false;
    }

    public GetMenuReviewsOutDto getMenuReviews(Long menuId) {

        Menu findMenu = menuRepository.findById(menuId)
                .orElseThrow(() -> new NotExistMenuException(NOT_EXIST_MENU));

        List<MenuReview> findMenuReviews = menuReviewRepository.findByMenu(findMenu).get();

        if (hasContent(findMenuReviews)) {
            return null;
        }

        List<GetMenuReviewOutDto> menuReview = findMenuReviews.stream()
                .map(r ->
                        GetMenuReviewOutDto.of(
                                r.getId(),
                                r.getReview().getUser().getProfile().getNickname(),
                                convertToMeokProfileVO(r.getReview().getUser().getMeokProfile()),
                                convertToMenuReviewVo(r)
                        )
                ).collect(Collectors.toList());

        return GetMenuReviewsOutDto.of(findMenu.getId(), menuReview);

    }

    private static boolean hasContent(List<MenuReview> findMenuReviews) {
        if (findMenuReviews.isEmpty()) {
            return true;
        }
        return false;
    }

    public GetMenuReviewOutDto getMenuReview(Long menuReviewId) {

        MenuReview menuReview = menuReviewRepository.findById(menuReviewId)
                .orElseThrow(() -> new NotExistMenuReviewException(NOT_EXIST_REVIEW));

        User user = menuReview.getReview().getUser();

        return GetMenuReviewOutDto.of(
                menuReview.getId(),
                user.getProfile().getNickname(),
                convertToMeokProfileVO(user.getMeokProfile()),
                convertToMenuReviewVo(menuReview)
        );

    }

    private MeokProfileVO convertToMeokProfileVO(MeokProfile meokProfile) {
        return MeokProfileVO.builder()
                .intensity(meokProfile.getIntensity())
                .preference(meokProfile.getPreference())
                .build();
    }

    private MenuReviewVO convertToMenuReviewVo(MenuReview menuReview) {
        return MenuReviewVO.builder()
                .content(menuReview.getContent())
                .recommendationStatus(menuReview.getRecommendationStatus())
                .images(getImageUrl(menuReview))
                .build();
    }

    private List<String> getImageUrl(MenuReview menuReview) {
        return menuReview.getMenuReviewImages().stream()
                .map(MenuReviewImage::getUrl)
                .collect(Collectors.toList());
    }

}
