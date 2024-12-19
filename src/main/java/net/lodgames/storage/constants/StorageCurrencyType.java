package net.lodgames.storage.constants;

import lombok.Getter;

@Getter
public enum StorageCurrencyType {
    DIAMOND(0),
    COIN(1),
    CHIP(2),
    FREE(3),
    EVENT(4);

    private final int value;
    StorageCurrencyType(int value) {
        this.value = value;
    }
}
