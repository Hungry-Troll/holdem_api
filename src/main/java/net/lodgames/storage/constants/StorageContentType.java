package net.lodgames.storage.constants;

import lombok.Getter;

@Getter
public enum StorageContentType {
    CURRENCY(0),
    ITEM(1),
    BUNDLE(2);

    private final int value;

    private StorageContentType(final int value) {
        this.value = value;
    }
}
