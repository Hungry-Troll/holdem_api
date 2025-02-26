package net.lodgames.chat.constant;

import lombok.Getter;

@Getter
public enum RoomSearchType {
    NAME("name");
    RoomSearchType(String typeName) {
        this.typeName = typeName;
    }
    private final String typeName;
}
