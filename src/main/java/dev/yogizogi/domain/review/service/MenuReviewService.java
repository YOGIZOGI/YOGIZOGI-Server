package dev.yogizogi.domain.review.service;

import static dev.yogizogi.global.common.code.ErrorCode.FAIL_TO_REVIEW_NO_PERMISSION_RESTAURANT;
import static dev.yogizogi.global.common.code.ErrorCode.NOT_EXIST_MENU;
import static dev.yogizogi.global.common.code.ErrorCode.NOT_EXIST_REVIEW;

import dev.yogizogi.domain.menu.exception.NotExistMenuException;
import dev.yogizogi.domain.menu.model.entity.Menu;
import dev.yogizogi.domain.menu.repository.MenuRepository;
import dev.yogizogi.domain.review.execption.NoPermissionRestaurantException;
import dev.yogizogi.domain.review.execption.NotExistReviewException;
import dev.yogizogi.domain.review.model.dto.request.CreateMenuReviewInDto;
import dev.yogizogi.domain.review.model.dto.response.CreateMenuReviewOutDto;
import dev.yogizogi.domain.review.model.dto.response.GetMenuReviewsOutDto;
import dev.yogizogi.domain.review.model.entity.MenuReview;
import dev.yogizogi.domain.review.model.entity.Review;
import dev.yogizogi.domain.review.model.entity.ReviewImage;
import dev.yogizogi.domain.review.repository.MenuReviewRepository;
import dev.yogizogi.domain.review.repository.ReviewImageRepository;
import dev.yogizogi.domain.review.repository.ReviewRepository;
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
    private final ReviewImageRepository reviewImageRepository;

    public CreateMenuReviewOutDto createMenuReview
            (UUID reviewId, Long menuId, String content, String recommend, List<String> imageUrl) {

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new NotExistReviewException(NOT_EXIST_REVIEW));

        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new NotExistMenuException(NOT_EXIST_MENU));

        if (menu.getRestaurant() != review.getRestaurant()) {
            throw new NoPermissionRestaurantException(FAIL_TO_REVIEW_NO_PERMISSION_RESTAURANT);
        }


        MenuReview menuReview = CreateMenuReviewInDto.toEntity(review, menu, content, recommend);
        menuReviewRepository.save(menuReview);

        List<ReviewImage> reviewImage = imageUrl.stream()
                .map(url -> ReviewImage.builder()
                        .menuReview(menuReview)
                        .url(url)
                        .build())
                .collect(Collectors.toList());

        reviewImageRepository.saveAll(reviewImage);

        return CreateMenuReviewOutDto.of(menuReview.getId(), menuReview.getReview().getId(), menuReview.getMenu().getId());

    }

    public List<GetMenuReviewsOutDto> getMenuReviews(Long menuId) {

        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new NotExistMenuException(NOT_EXIST_MENU));

        List<MenuReview> menuReviews = menuReviewRepository.findByMenu(menu)
                .orElseGet(null);

        return menuReviews.stream()
                .map(menuReview ->
                    GetMenuReviewsOutDto.of(
                            menuReview.getId(),
                            menuReview.getReviewImages().stream().map(ReviewImage::getUrl).collect(Collectors.toList()),
                            menuReview.getRecommendationStatus())
                )
                .collect(Collectors.toList());

    }

}
