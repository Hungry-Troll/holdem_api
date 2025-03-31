package net.lodgames.society.constants;

import lombok.Getter;

@Getter
public enum MemberType {
    NORMAL(0),
    OPERATOR(1),
    LEADER(2);

    MemberType(int typeNum) {
        this.typeNum = typeNum;
    }

    private final int typeNum;
}
