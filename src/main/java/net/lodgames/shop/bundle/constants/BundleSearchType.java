package net.lodgames.shop.bundle.constants;

import lombok.Getter;

@Getter
public enum BundleSearchType {
    NAME("name"),
    SKU("sku"),
    ;
    private final String typeName;
    BundleSearchType(String typeName) {
        this.typeName = typeName;
    }
}
