package net.lodgames.currency.diamond.param;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DiamondWithdrawParam {
    @JsonIgnore
    private Long userId;
    private Long amount;
    private String idempotentKey;
}
