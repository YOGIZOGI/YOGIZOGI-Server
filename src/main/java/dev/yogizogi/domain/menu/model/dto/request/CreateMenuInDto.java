package dev.yogizogi.domain.menu.model.dto.request;

import dev.yogizogi.domain.menu.model.entity.Menu;
import dev.yogizogi.domain.menu.model.entity.MenuDetails;
import dev.yogizogi.domain.restaurant.model.entity.Restaurant;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "메뉴 등록 요청 DTO")
public class CreateMenuInDto {

    @Schema(description = "음식점", example = "3")
    private UUID restaurantId;

    @Schema(description = "이름", example = "2인 사시미")
    private String name;

    @Schema(description = "가격", example = "29000")
    private String price;

    @Schema(description = "설명", example = "연어, 광어, 참치, 참돔, 아나고, 농어, 숭어로 구성")
    private String description;

    @Schema(description = "사진", example = "https://yogizogi-multimedia-bucket.s3.ap-northeast-2.amazonaws.com/menu/yobi/2144bbea-0aaf-47b3-85ed-5baf1d6ce2da")
    private String imageUrl;

    @Builder
    public static Menu toEntity(Restaurant restaurant, String name, String price, String description, String imageUrl) {
        return Menu.builder()
                .restaurant(restaurant)
                .details(MenuDetails.builder()
                        .name(name)
                        .price(price)
                        .description(description)
                        .imageUrl(imageUrl)
                        .build())
                .build();
    }

}
