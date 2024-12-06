package net.lodgames.storage.constants;

import lombok.Getter;

@Getter
public enum StorageSenderType {
    USER(0),
    ADMIN(1),;

    private final Integer value;

    StorageSenderType(Integer value) {
        this.value = value;
    }
}
