package net.lodgames.chat.constant;


import lombok.Getter;

@Getter
public enum TargetStatus {
    NORMAL(0),
    DELETE(1),
    BLOCK(2);

    TargetStatus(int statusNum) {
        this.statusNum = statusNum;
    }

    private final int statusNum;

}
