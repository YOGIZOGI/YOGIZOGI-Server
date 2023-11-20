package dev.yogizogi.domain.meokmap.factory.entity;

import static dev.yogizogi.domain.meokmap.factory.fixtures.MeokMapFixtures.사용자;

import dev.yogizogi.domain.meokmap.model.entity.MeokMap;

public class MeokMapFactory {

    public static MeokMap createMeokMap() {

        return MeokMap.builder()
                .user(사용자)
                .build();

    }

}
