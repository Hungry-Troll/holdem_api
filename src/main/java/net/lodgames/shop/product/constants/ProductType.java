package net.lodgames.shop.product.constants;

import lombok.Getter;

@Getter
public enum ProductType {
    CURRENCY(0), // 재화
    ;
    private final int value;

    ProductType(int value) {
        this.value = value;
    }

}
