package net.lodgames.shop.collection.constants;

import lombok.Getter;

@Getter
public enum CollectionActivation {
    ACTIVATION(0),
    DEACTIVATION(1)
    ;
    private final int value;
    CollectionActivation(int value) {
        this.value = value;
    }
}
