package net.lodgames.currency.common.constants;

import lombok.Getter;

@Getter
public enum CurrencyType {
    DIAMOND(0),
    COIN(1),
    CHIP(2),
    FREE(3),
    EVENT(4)
    ;
    private final int value;

    CurrencyType(int value) {
        this.value = value;
    }

}
