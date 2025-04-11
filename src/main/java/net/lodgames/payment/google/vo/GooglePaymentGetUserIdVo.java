package net.lodgames.payment.google.vo;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GooglePaymentGetUserIdVo {
    private String nickname;
    private Long userId;
    private String productId;
    private String orderId;
    private String googlePaymentLog;
}
