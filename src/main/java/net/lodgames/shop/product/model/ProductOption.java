package net.lodgames.shop.product.model;


import jakarta.persistence.*;
import lombok.*;
import net.lodgames.shop.product.constants.ProductOptionType;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class ProductOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                 // 상품 옵션 고유번호
    private Long productId;          // 상품 아이디
    private String name;             // 이름
    private ProductOptionType type;  // 상품 옵션 타입 DIAMOND(0), COIN(1), ITEM(2)
    private Integer quantity;        // 갯수
    @CreatedDate
    private LocalDateTime createdAt; // 생성시각
}