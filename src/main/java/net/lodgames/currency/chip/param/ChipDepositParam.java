package net.lodgames.currency.chip.param;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChipDepositParam {
    @JsonIgnore
    private Long userId;
    private Long amount;
    private String idempotentKey;
}
