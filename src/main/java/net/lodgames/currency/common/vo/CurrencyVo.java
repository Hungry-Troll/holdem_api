package net.lodgames.currency.common.vo;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyVo {
    private Long userId;
    private Long coinAmount;    // 코인 합계
    private Long chipAmount;    // 칩 합계
    private Long diamondAmount; // 다이아몬드 합계
}
