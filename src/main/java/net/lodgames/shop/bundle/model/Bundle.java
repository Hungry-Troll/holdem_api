package net.lodgames.shop.bundle.model;

import jakarta.persistence.*;
import lombok.*;
import net.lodgames.currency.common.constants.CurrencyType;
import net.lodgames.shop.bundle.constants.BundleStatus;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
@Table(name = "bundle")
public class Bundle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                     // 번들 고유 번호
    private String name;                 // 이름
    private String sku;                  // sku
    private String description;          // 설명
    private BundleStatus status;         // 상태
    private String thumbnail;            // 썸네일
    private String image;                // 이미지
    private String info;                 // 정보
    private Integer countPerPerson;      // 한명당 구매 가능 개수
    private LocalDateTime saleStartDate; // 판매 시작일
    private LocalDateTime saleEndDate;   // 판매 종료일
    private CurrencyType currencyType;   // 재화타입 (구매)
    private Integer amount;              // 판매가격
    private Integer originAmount;        // 원판매가격
    private Integer stockQuantity;       // 재고갯수
    @OneToMany(mappedBy = "bundle", cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    private List<BundleItem> bundleItems;  // bad naming.
    @CreatedDate
    private LocalDateTime createdAt; // 만든날짜
    @LastModifiedDate
    private LocalDateTime updatedAt; // 변경일



}