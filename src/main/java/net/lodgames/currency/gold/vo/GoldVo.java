package net.lodgames.currency.gold.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class GoldVo {
    private Long userId;
    private Long amount;
}
