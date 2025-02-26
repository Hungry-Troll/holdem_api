package net.lodgames.chat.constant;


import lombok.Getter;

@Getter
public enum ParticipantType {
    MEMBER(0),
    OWNER(1);

    ParticipantType(int typeNum) {
        this.typeNum = typeNum;
    }

    private final int typeNum;
}
