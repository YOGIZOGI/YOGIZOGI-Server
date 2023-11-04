package dev.yogizogi.domain.menu.model.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MenuDetails {

    @Schema(description = "이름", example = "2인 사시미")
    private String name;

    @Schema(description = "가격", example = "29000")
    private String price;

    @Schema(description = "설명", example = "연어, 광어, 참치, 참돔, 아나고, 농어, 숭어로 구성")
    private String description;

    @Column(columnDefinition = "TEXT")
    @Schema(description = "사진", example = "https://yogizogi-multimedia-bucket.s3.ap-northeast-2.amazonaws.com/menu/yobi/2144bbea-0aaf-47b3-85ed-5baf1d6ce2da")
    private String imageUrl;

    @Builder
    public MenuDetails(String name, String price, String description, String imageUrl) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.imageUrl = imageUrl;
    }

}

