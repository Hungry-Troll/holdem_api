package net.lodgames.currency.diamond.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class DiamondVo {
    private Long userId;
    private Long amount;
    private Long paidAmount;
}
