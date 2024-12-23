package net.lodgames.currency.param;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DiamondDepositParam {
    @JsonIgnore
    private Long userId;
    private Long amount;
    private String idempotentKey;
}
