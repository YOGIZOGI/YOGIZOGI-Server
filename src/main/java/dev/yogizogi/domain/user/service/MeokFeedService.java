package dev.yogizogi.domain.user.service;

import static dev.yogizogi.global.common.code.ErrorCode.NOT_EXIST_USER;

import dev.yogizogi.domain.review.model.entity.MenuReview;
import dev.yogizogi.domain.review.model.entity.MenuReviewImage;
import dev.yogizogi.domain.review.model.entity.Review;
import dev.yogizogi.domain.review.model.vo.MenuReviewVO;
import dev.yogizogi.domain.review.repository.MenuReviewRepository;
import dev.yogizogi.domain.review.repository.ReviewRepository;
import dev.yogizogi.domain.user.exception.NotExistUserException;
import dev.yogizogi.domain.user.model.dto.response.RetrieveMeokFeedOutDto;
import dev.yogizogi.domain.user.model.entity.User;
import dev.yogizogi.domain.user.repository.UserRepository;
import dev.yogizogi.global.common.status.BaseStatus;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MeokFeedService {

    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;
    private final MenuReviewRepository menuReviewRepository;

    public RetrieveMeokFeedOutDto retrieveMeokFeed(UUID userId) {

        User findUser = userRepository.findByIdAndStatus(userId, BaseStatus.ACTIVE)
                .orElseThrow(() -> new NotExistUserException(NOT_EXIST_USER));

        List<Review> reviews = reviewRepository.findAllByUserId(userId).get();

        if (reviews.isEmpty()) {
            return null;
        }

        List<MenuReview> menuReviews = getMenuReviews(reviews);

        return  RetrieveMeokFeedOutDto.of(
                findUser.getProfile(),
                menuReviews.stream()
                        .map(this::convertToMenuReviewVO)
                        .collect(Collectors.toList()));

    }

    private List<MenuReview> getMenuReviews(List<Review> reviews) {
        return reviews.stream()
                .flatMap(r -> menuReviewRepository.findAllByReview(r).get().stream())
                .collect(Collectors.toList());
    }

    private MenuReviewVO convertToMenuReviewVO(MenuReview menuReview) {
        return  MenuReviewVO.builder()
                .id(menuReview.getId())
                .recommendationStatus(menuReview.getRecommendationStatus())
                .images(getImageUrls(menuReview))
                .build();
    }

    private List<String> getImageUrls(MenuReview menuReview) {
        return menuReview.getMenuReviewImages()
                .stream()
                .map(MenuReviewImage::getUrl)
                .collect(Collectors.toList());
    }

}
