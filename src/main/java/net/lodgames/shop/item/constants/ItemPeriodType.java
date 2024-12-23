package net.lodgames.shop.item.constants;

public enum ItemPeriodType {
    NONE(0),
    DAY(1),
    MONTH(2),
    EXPIRATION(2)
    ;
    private final int value;
    ItemPeriodType(int value) {
        this.value = value;
    }
}
// none, day, expiration