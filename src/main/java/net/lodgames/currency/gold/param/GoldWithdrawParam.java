package net.lodgames.currency.gold.param;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GoldWithdrawParam {
    @JsonIgnore
    private Long userId;
    private Long amount;
    private String idempotentKey;
}
