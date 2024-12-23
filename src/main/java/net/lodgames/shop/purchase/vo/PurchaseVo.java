package net.lodgames.shop.purchase.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.lodgames.currency.constants.CurrencyType;
import net.lodgames.shop.bundle.vo.BundleVo;
import net.lodgames.shop.item.vo.ItemVo;
import net.lodgames.shop.purchase.constants.PurchaseType;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
public class PurchaseVo {
    private Long id;                   // 고유번호
    private BundleVo bundle;           // 번들 정보
    private ItemVo item;               // 아이템 정보
    private Long userId;               // 유저 고유번호
    private PurchaseType purchaseType; // 구매타입
    private CurrencyType currencyType; // 재화타입
    private Integer paidAmount;        // 구매금액
    private LocalDateTime canceledAt;  // 취소날짜
    private LocalDateTime createdAt;   // 만든날짜
}
