package net.lodgames.shop.item.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.lodgames.currency.constants.CurrencyType;
import net.lodgames.shop.item.constants.ItemPeriodType;
import net.lodgames.shop.item.constants.ItemStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ItemVo {
    private Long id;                   // 아이템 고유번호
    private Long categoryId;           // 카테고리
    private Long itemUnitId;           // 아이템 유닛 고유번호
    private String sku;                // 아이템 SKU
    private String unitSku;            // 아이템 유닛 SKU
    private String name;               // 아이템 이름
    private String description;        // 설명
    private Integer num;               // 아이템 갯수
    private Integer stockQuantity;     // 재고갯수
    private ItemStatus status;         // 상태
    private String thumbnail;          // 썸네일
    private String image;              // 이미지
    private String info;               // 정보
    private ItemPeriodType periodType; // 기간타입
    private Integer period;            // 기간
    private LocalDateTime expiration;  // 만료일
    private CurrencyType currencyType; // 재화타입
    private Integer amount;            // 재화가격
    private LocalDateTime createdAt;   // 만든날짜
    private LocalDateTime updatedAt;   // 변경일
}
