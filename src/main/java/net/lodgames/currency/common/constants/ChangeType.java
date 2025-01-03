package net.lodgames.currency.common.constants;

import lombok.Getter;

@Getter
public enum ChangeType {
    ADD(0),
    USE(1);

    private final int changeType;

    ChangeType(int changeType) {
        this.changeType = changeType;
    }
}
