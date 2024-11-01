package net.lodgames.shop.item.constants;

import lombok.Getter;

@Getter
public enum ItemUnitSearchType {
    NAME("name"), // 이름
    SKU("sku"),   // sku
    ;
    private final String typeName;
    ItemUnitSearchType(String typeName) {
        this.typeName = typeName;
    }
}
