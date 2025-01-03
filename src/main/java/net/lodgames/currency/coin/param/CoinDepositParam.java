package net.lodgames.currency.coin.param;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CoinDepositParam {
    @JsonIgnore
    private Long userId;
    private Long amount;
    private String idempotentKey;
}
