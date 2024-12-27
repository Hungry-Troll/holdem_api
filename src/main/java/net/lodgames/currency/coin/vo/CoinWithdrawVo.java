package net.lodgames.currency.coin.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class CoinWithdrawVo {
    private Long userId;
    private Long amount;
    private Long resultAmount;
    private String idempotentKey;

}
