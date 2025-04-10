package net.lodgames.payment.google.vo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GooglePaymentGetNicknameVo {
    private String nickname;
    private Long userId;
    private String productId;
    private String orderId;
    private String googlePaymentLog;
}
