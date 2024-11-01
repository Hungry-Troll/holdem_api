package net.lodgames.stuff.constants;

import lombok.Getter;

public enum StuffSearchDateType {
    CREATED_AT("createdAt"),
    UPDATED_AT("updatedAt"),
    MAKE_DATETIME("makeDatetime");
    private String typeName;

    StuffSearchDateType(String typeName) {
        this.typeName = typeName;
    }
}
