package net.lodgames.shop.payment.param;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentParam {
    private Long orderId;         // 주문고유번호
    private Long userId;          // 유저고유번호
    private Integer amount;       // 결제금액
    private String method;        // 결제수단
    private String purchaseToken; // 구매 토큰
}