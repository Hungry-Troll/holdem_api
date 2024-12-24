package net.lodgames.shop.product.model;

import jakarta.persistence.*;
import lombok.*;
import net.lodgames.shop.product.constants.ProductStatus;
import net.lodgames.shop.product.constants.ProductType;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                 // 상품 고유번호
    private String name;             // 이름
    private String description;      // 설명
    private ProductStatus status;    // 상태 (준비중, 판매중, 판매중지, 게시중지)
    private String thumbnail;        // 썸네일 경로
    private String image;            // 이미지 경로
    private String info;             // 정보
    private ProductType type;        // 상품 종류
    private Integer stockQuantity;   // 갯수 (null 이면 무제한)
    private Integer price;           // 판매가격
    private Integer originPrice;     // 원가격
    @CreatedDate
    private LocalDateTime createdAt; // 생성시각
    @LastModifiedDate
    private LocalDateTime updatedAt; // 변경시각
}
