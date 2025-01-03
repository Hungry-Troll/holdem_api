package net.lodgames.currency.chip.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class ChipWithdrawVo {
    private Long userId;
    private Long amount;
    private Long resultAmount;
    private String idempotentKey;

}
