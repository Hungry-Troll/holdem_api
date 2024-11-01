package net.lodgames.shop.item.constants;

public enum ItemSearchType {
    NAME("name"),
    SKU("sku"),
    ;
    private final String typeName;
    ItemSearchType(String typeName) {
        this.typeName = typeName;
    }
}
