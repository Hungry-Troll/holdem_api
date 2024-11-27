package net.lodgames.board.constants;

import lombok.Getter;

@Getter
public enum BoardType {
    NOTICE(0),
    EVENT(1),
    ;

    private final int type;

    BoardType(int type) {
        this.type = type;
    }
}
