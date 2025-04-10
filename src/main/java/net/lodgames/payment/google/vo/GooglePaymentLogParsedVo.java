package net.lodgames.payment.google.vo;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class GooglePaymentLogParsedVo { // GooglePaymentLog 파싱한다면 사용할 수 있음
    private String purchaseTimeMillis;
    private Integer purchaseState;
    private Integer consumptionState;
    private String developerPayload;
    private String orderId;
    private Integer purchaseType;
    private Integer acknowledgementState;
    private String kind;
    private String regionCode;
}
