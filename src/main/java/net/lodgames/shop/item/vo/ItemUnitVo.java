package net.lodgames.shop.item.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.lodgames.shop.item.constants.ItemUnitType;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ItemUnitVo {
    private Long id;                 // 아이템 유닛 고유번호
    private String sku;              // sku
    private String name;             // 이름
    private String image;            // 이미지
    private String description;      // 설명
    private String attributes;       // 속성
    private ItemUnitType type;       // 타입
    private LocalDateTime createdAt; // 만든날짜
    private LocalDateTime updatedAt; // 변경일
}
