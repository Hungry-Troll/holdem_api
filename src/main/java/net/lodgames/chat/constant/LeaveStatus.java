package net.lodgames.chat.constant;


import lombok.Getter;
// 떠남 여부
@Getter
public enum LeaveStatus {
    LEAVE(0),
    STAY(1);

    LeaveStatus(int statusNum) {
        this.statusNum = statusNum;
    }

    private final int statusNum;

}
