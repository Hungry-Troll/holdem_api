package net.lodgames.shop.purchase.param;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PurchaseParam {
    @JsonIgnore
    private Long userId;
    private Long bundleId;
    private Long itemId;
    // private CurrencyType currencyType;
    // private Integer paidAmount;
}
