package net.lodgames.shop.item.model;

import jakarta.persistence.*;
import lombok.*;
import net.lodgames.shop.item.constants.ItemUnitType;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@EntityListeners({AuditingEntityListener.class})
public class ItemUnit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;            // 고유번호
    private String sku;         // sku
    private String name;        // 이름
    private String image;       // 이미지
    private String description; // 설명
    private String attributes;  // 속성
    private ItemUnitType type;  // 유형 CONSUMABLE(0) 소모성 , PERMANENT(1) 영구 , EXPIRATION(2) 기간제
    @LastModifiedDate
    private LocalDateTime updatedAt; // 변경일
    @CreatedDate
    private LocalDateTime createdAt; // 만든날짜

}


