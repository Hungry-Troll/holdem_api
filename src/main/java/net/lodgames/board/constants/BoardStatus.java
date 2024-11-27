package net.lodgames.board.constants;

import lombok.Getter;

@Getter
public enum BoardStatus {
    NOT_PUBLISH(0),
    PUBLISH(1),
    ;

    private final int status;

    BoardStatus(int status) {
        this.status = status;
    }
}
