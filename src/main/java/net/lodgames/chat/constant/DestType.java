package net.lodgames.chat.constant;


import lombok.Getter;

@Getter
public enum DestType {
    DM(0),
    ROOM(1);

    DestType(int typeNum) {
        this.typeNum = typeNum;
    }

    private final int typeNum;

}
