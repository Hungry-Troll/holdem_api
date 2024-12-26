package net.lodgames.shop.item.constants;

import lombok.Getter;

@Getter
public enum ItemPeriodType {
    NONE(0),      // 없음
    DAY(1),       // 일단위
    MONTH(2),     // 월단위
    EXPIRATION(3) // 기한
    ;
    private final int value;
    ItemPeriodType(int value) {
        this.value = value;
    }
}
// none, day, expiration