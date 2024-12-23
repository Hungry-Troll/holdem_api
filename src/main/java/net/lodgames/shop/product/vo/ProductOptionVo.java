package net.lodgames.shop.product.vo;

import lombok.Getter;
import lombok.Setter;
import net.lodgames.shop.product.constants.ProductOptionType;

import java.time.LocalDateTime;

@Getter
@Setter
public class ProductOptionVo {
    private Long id;                 // 상품 옵션 고유번호
    private Long productId;          // 상품 아이디
    private String name;             // 이름
    private ProductOptionType type;  // 상품 옵션 타입 DIAMOND(0), COIN(1), ITEM(2)
    private Integer quantity;        // 갯수
    private LocalDateTime createdAt; // 생성시각
}
