package dev.yogizogi.domain.menu.factory.entity;

import dev.yogizogi.domain.menu.model.entity.MenuVO;
import java.util.List;
import java.util.stream.Collectors;

public class MenuVOFactory {

    public static List<MenuVO> menuVOs() {

        return MenuFactory.createMenus().stream()
                .map(menu
                        -> MenuVO.builder()
                        .id(menu.getId())
                        .details(menu.getDetails())
                        .build()
                ).collect(Collectors.toList());

    }

}
