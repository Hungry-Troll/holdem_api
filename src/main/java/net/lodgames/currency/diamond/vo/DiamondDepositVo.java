package net.lodgames.currency.diamond.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class DiamondDepositVo {
    private Long userId;
    private Long amount;
    private Long resultAmount;
    private String idempotentKey;

}
