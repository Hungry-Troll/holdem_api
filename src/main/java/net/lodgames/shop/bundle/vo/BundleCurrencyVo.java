package net.lodgames.shop.bundle.vo;

import lombok.Getter;
import lombok.Setter;
import net.lodgames.currency.constants.CurrencyType;

import java.time.LocalDateTime;

@Getter
@Setter
public class BundleCurrencyVo {
    private Long id;                   // 번들재화 고유번호
    private Long bundleId;             // 번들 고유번호
    private CurrencyType currencyType; // COIN Only
    private Integer count;             // 갯수
    private LocalDateTime createdAt;   // 만든날짜
}
