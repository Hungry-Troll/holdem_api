package net.lodgames.chat.constant;

import lombok.Getter;
@Getter
public enum MsgType {
    NORMAL("normal","message.normal"),  // 일반메시지
    MODIFY("modify","message.modify"),  // 변경메시지
    DELETE("delete", "message.delete"), // 삭제메시지
    ALARM("alarm","message.alarm")     // 알림메시지
    ;
    MsgType(String typeString, String fullTypeString) {
        this.typeString = typeString;
        this.fullTypeString = fullTypeString;
    }
    private final String typeString;
    private final String fullTypeString;
}
