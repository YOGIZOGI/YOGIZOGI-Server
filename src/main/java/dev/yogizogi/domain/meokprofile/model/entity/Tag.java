package dev.yogizogi.domain.meokprofile.model.entity;

import static dev.yogizogi.global.common.code.ErrorCode.*;

import java.util.List;
import java.util.stream.Collectors;

import dev.yogizogi.domain.meokprofile.exception.InvalidTagException;
import dev.yogizogi.domain.restaurant.exception.InvalidRestaurantTypeException;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Tag {

	LOVE_SPICY("맵고수"),
	LOVE_SWEETNESS("슈가"),
	LOVE_SALTY(""),
	LOVE_MINT("민초단"),
	WELL_KNOW_FOOD("먹잘알");

	private String description;

	public static Tag fromDescription(String description) {

		for (Tag tag : Tag.values()) {
			if (tag.getDescription().equals(description)) {
				return tag;
			}
		}

		throw new InvalidRestaurantTypeException(INVALID_RESTAURANT_TYPE);

	}

	public static List<Tag> convertToEnum(List<String> tags) {
		try {
			return tags.stream()
				.map(Tag::valueOf)
				.collect(Collectors.toList());
		} catch (IllegalArgumentException e) {
			throw new InvalidTagException(INVALID_MEOK_TAG);
		}
	}

	public static List<String> convertToString(List<Tag> tags) {
		return tags.stream()
			.map(Tag::getDescription)
			.collect(Collectors.toList());
	}

}
