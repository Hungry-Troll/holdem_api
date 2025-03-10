package net.lodgames.currency.diamond.param;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DiamondCheatParam {
    @JsonIgnore
    private Long userId;
    private Long freeAmount;
    private Long androidAmount;
    private Long iosAmount;
    private Long paidAmount;
}
