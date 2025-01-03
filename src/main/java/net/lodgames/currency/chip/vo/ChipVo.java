package net.lodgames.currency.chip.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class ChipVo {
    private Long userId;
    private Long amount;
}
