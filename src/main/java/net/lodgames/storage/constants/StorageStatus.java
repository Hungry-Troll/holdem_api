package net.lodgames.storage.constants;

import lombok.Getter;

@Getter
public enum StorageStatus {
    WAITING(0), // 수령 전
    RECEIVED(1),; // 수령 완료

    private final int value;

    StorageStatus(int value) {
        this.value = value;
    }
}
