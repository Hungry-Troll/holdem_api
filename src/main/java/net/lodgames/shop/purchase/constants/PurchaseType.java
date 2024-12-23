package net.lodgames.shop.purchase.constants;

import lombok.Getter;

@Getter
public enum PurchaseType {
    BUY(0),
    FREE(1),
    EVENT(2)
    ;
    private final int value;
    PurchaseType(int value) {
        this.value = value;
    }

}
