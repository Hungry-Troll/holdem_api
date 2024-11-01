package net.lodgames.shop.order.constants;

import lombok.Getter;

@Getter
public enum OrderStatus {
    ACCEPT(0),  // 접수
    PAID(1),    // 지불
    CANCEL(2),  // 취소
    REFUND(3);  // 환불

    private final int value;

    OrderStatus(int value) {
        this.value = value;
    }
}
