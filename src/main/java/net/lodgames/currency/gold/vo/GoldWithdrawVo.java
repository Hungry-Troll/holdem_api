package net.lodgames.currency.gold.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class GoldWithdrawVo {
    private Long userId;
    private Long amount;
    private Long resultAmount;
    private String idempotentKey;

}
