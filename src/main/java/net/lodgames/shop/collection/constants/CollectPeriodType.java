package net.lodgames.shop.collection.constants;

import lombok.Getter;

@Getter
public enum CollectPeriodType {
    NONE(0),
    EXPIRATION(1)
    ;
    private final int value;
    CollectPeriodType(int value) {
        this.value = value;
    }
}
// none, day, expiration