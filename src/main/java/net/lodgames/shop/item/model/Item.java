package net.lodgames.shop.item.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.lodgames.currency.constants.CurrencyType;
import net.lodgames.shop.item.constants.ItemPeriodType;
import net.lodgames.shop.item.constants.ItemStatus;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "item")
@EntityListeners({AuditingEntityListener.class})
@Builder
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                   // 아이템 고유번호
    private Long categoryId;           // 카테고리 고유번호
    private Long itemUnitId;           // 아이템 유닛 고유번호
    private String sku;                // sku
    private String unitSku;            // 아이템 유닛 sku
    private String name;               // 이름
    private String description;        // 설명
    private int num;                   // 아이템 수
    private Integer stockQuantity;     // 재고 갯수
    private ItemStatus status;         // 상태 : READY(0) 준비중, ON_SALE(1) 판매중, STOP_SELLING(2) 판매중지 , REMOVED(3) 제거됨
    private String thumbnail;          // 썸네일
    private String image;              // 이미지
    private String info;               // 정보
    private ItemPeriodType periodType; // 기간 NONE(0), DAY(1), MONTH(2), EXPIRATION(2)
    private Integer period;            // 기간
    private LocalDateTime expiration;  // 민려
    private CurrencyType currencyType; // DIAMOND(0), COIN(1), CHIP(2), FREE(3), EVENT(4)
    private Integer amount;            // 가격
    @CreatedDate
    private LocalDateTime createdAt;   // 만든날짜
    @LastModifiedDate
    private LocalDateTime updatedAt  ; // 변경일

}
