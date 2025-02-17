package net.lodgames.stamina.constants;

import lombok.Getter;

@Getter
public enum AcquireType {
    ACQUIRE_TYPE_ONE(0, 10),
    ACQUIRE_TYPE_TWO(1, 20),
    ACQUIRE_TYPE_THREE(2, 30),
    ACQUIRE_TYPE_FOUR(3, 40);

    final int status;
    final int acquireValue;

    AcquireType(int status, int acquireValue){
        this.status = status;
        this.acquireValue = acquireValue;
    }
}
