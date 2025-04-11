package net.lodgames.payment.google.constants;

import lombok.Getter;

@Getter
public enum GooglePaymentPurchaseState {
    //0.Purchased  1.Canceled  2.Pending
    PURCHASED(0),
    CANCELED(1),
    PENDING(2);
    final int status;

    GooglePaymentPurchaseState(int status){
        this.status = status;
    }
}
