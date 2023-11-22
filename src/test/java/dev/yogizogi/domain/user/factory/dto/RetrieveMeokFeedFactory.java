package dev.yogizogi.domain.user.factory.dto;

import dev.yogizogi.domain.review.factory.entity.MenuReviewVOFactory;
import dev.yogizogi.domain.user.factory.entity.UserFactory;
import dev.yogizogi.domain.user.model.dto.response.RetrieveMeokFeedOutDto;

public class RetrieveMeokFeedFactory {

    public static RetrieveMeokFeedOutDto retrieveMeokFeedOutDto() {
        return RetrieveMeokFeedOutDto.of(UserFactory.createUserWithProfile().getProfile(), MenuReviewVOFactory.creatMenuReviewVOs());
    }

    public static RetrieveMeokFeedOutDto retrieveMeokFeedOutDtoNoContent() {
        return null;
    }

}
