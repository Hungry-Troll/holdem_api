package net.lodgames.society.constants;

import lombok.Getter;

@Getter
public enum WaitType {
    APPLY(0),   // 지원
    INVITE(1) , // 초대
    BAN(2);     // 차단

    WaitType(int typeNum) {
        this.typeNum = typeNum;
    }

    private final int typeNum;
}
