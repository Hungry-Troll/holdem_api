package net.lodgames.chat.constant;


import lombok.Getter;

@Getter
public enum RoomType {
    ANYONE(0),  // 공개 (임의 참가 가능
    SECURE(1),  // 보안 ( 임의 참가시 보안코드 필요 )
    PRIVATE(2); // 비공개 ( 임의 참가 불가 , 초대만 가능)

    RoomType(int typeNum) {
        this.typeNum = typeNum;
    }

    private final int typeNum;

}
