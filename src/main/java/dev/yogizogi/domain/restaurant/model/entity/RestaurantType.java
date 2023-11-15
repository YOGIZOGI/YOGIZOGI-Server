package dev.yogizogi.domain.restaurant.model.entity;

import static dev.yogizogi.global.common.code.ErrorCode.NOT_EXIST_CUISINE;

import dev.yogizogi.domain.restaurant.exception.NotExistCuisineException;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RestaurantType {

    한식("KOREAN"),
    중식("CHINESE"),
    양식("WESTERN"),
    일식("JAPANESE"),
    태국식("THAI"),
    베트남식("VIETNAMESE"),
    인도식("INDIAN"),
    퓨전("FUSION");

    private String description;

    public static RestaurantType fromDescription(String description) {

        for (RestaurantType c : RestaurantType.values()) {
            if (c.getDescription().equals(description)) {
                return c;
            }
        }

        throw new NotExistCuisineException(NOT_EXIST_CUISINE);

    }

}
