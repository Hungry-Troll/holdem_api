package net.lodgames.shop.item.constants;

import lombok.Getter;

@Getter
public enum ItemUnitType {
    CONSUMABLE(0), // 소모성
    PERMANENT(1),  // 영구
    EXPIRATION(2)  // 기간제
    ;
    private final int value;
    ItemUnitType(int value) {
        this.value = value;
    }
}
