package net.lodgames.society.constants;


import lombok.Getter;

@Getter
public enum JoinType {
    FREE(0),   // 자유 가입
    PERMIT(1), // 허가제
    LOCK(2);   // 비번 가입

    JoinType(int typeNum) {
        this.typeNum = typeNum;
    }

    private final int typeNum;

}
