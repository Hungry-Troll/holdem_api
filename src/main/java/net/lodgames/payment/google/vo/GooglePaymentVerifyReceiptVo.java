package net.lodgames.payment.google.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import net.lodgames.payment.google.constants.GooglePaymentPurchaseState;

@Builder
@Getter
@Setter
public class GooglePaymentVerifyReceiptVo {
    private GooglePaymentPurchaseState purchaseState;
    private String orderId;
    private String productId;
}
