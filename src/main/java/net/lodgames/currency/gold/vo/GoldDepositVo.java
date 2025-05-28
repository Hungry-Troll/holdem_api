package net.lodgames.currency.gold.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class GoldDepositVo {
    private Long userId;
    private Long amount;
    private Long resultAmount;
    private String idempotentKey;

}
