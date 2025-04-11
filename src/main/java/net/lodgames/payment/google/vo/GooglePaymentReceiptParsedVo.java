package net.lodgames.payment.google.vo;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GooglePaymentReceiptParsedVo {
    private String packageName;
    private String productId;
    private String purchaseToken;
    private String orderId;
}
