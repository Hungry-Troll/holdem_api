package net.lodgames.shop.bundle.constants;

import lombok.Getter;

@Getter
public enum BundleStatus {
    // 준비중 , 판매중, 판매중지, 제거됨
    READY(0),           // 준비중
    ON_SALE(1),         // 판매중
    STOP_SELLING(2),    // 판매중지
    REMOVED(3);         // 제거됨

    private final int value;

    BundleStatus(int value) {
        this.value = value;
    }

}
