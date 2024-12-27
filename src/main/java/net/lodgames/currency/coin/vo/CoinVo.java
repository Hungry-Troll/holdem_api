package net.lodgames.currency.coin.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class CoinVo{
    private Long userId;
    private Long amount;
}
