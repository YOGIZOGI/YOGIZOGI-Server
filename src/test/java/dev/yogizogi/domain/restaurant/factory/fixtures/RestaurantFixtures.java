package dev.yogizogi.domain.restaurant.factory.fixtures;

import dev.yogizogi.global.util.UuidUtils;
import java.util.List;
import java.util.UUID;

public class RestaurantFixtures {

    public static UUID 식별자 = UuidUtils.createSequentialUUID();
    public static String 상호명 = "요비";
    public static String 전화번호 = "0507-1399-1080";
    public static String 주소 = "서울 광진구 아차산로29길 34";
    public static String 음식점_사진 = "https://yogizogi-multimedia-bucket.s3.ap-northeast-2.amazonaws.com/restaurant/1/b95c3147-d7a5-4c19-9908-fb6a2cd7bdca";
    public static String 위도 = "37.5422815915509";
    public static String 경도 = "127.068903207917";
    public static List<String> 음식점_종류 = List.of("한식", "일식", "중식", "양식", "베트남식", "인도식", "태국식", "퓨전");
    public static List<String> 잘못된_음식점_종류 = List.of("잘못됨");

}
