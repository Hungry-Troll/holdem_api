package net.lodgames.stuff.constants;

public enum StuffSearchType {
    NAME("name"),
    DESCRIPTION("description"),
    ID("id");
    private String typeName;
    StuffSearchType(String typeName) {
        this.typeName = typeName;
    }
}
