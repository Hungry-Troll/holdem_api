package net.lodgames.currency.diamond.param;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import net.lodgames.user.constants.Os;

@Getter
@Setter
public class DiamondDepositParam {
    @JsonIgnore
    private Long userId;
    private Os os;
    private Long amount;
    private String idempotentKey;
}
