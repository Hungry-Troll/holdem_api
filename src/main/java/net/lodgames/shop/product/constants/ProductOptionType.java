package net.lodgames.shop.product.constants;

import lombok.Getter;

@Getter
public enum ProductOptionType {
    DIAMOND(0),
    COIN(1),
    ITEM(2)
    ;
    private final int value;

    ProductOptionType(int value) {
        this.value = value;
    }

}
