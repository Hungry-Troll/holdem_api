package net.lodgames.society.constants;

import lombok.Getter;

@Getter
public enum WaitType {
    APPLY(0),
    INVITE(1) ,
    BAN(2);

    WaitType(int typeNum) {
        this.typeNum = typeNum;
    }

    private final int typeNum;
}
