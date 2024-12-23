package net.lodgames.shop.deposit.model;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity(name = "deposit_record")
@EntityListeners({AuditingEntityListener.class})
@Builder
public class DepositRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                   // 입금고유번호
    private Long orderId;              // 주문고유번호
    private Long productId;            // 상품 고유번호
    private Integer paymentPrice;      // 지불금액
    private LocalDateTime paymentDate; // 지불일
    private Long userId;               // 유저고유번호
    private String ci;                 // 유저연계정보
    @CreatedDate
    private LocalDateTime createdAt;   // 만든날짜
    @Builder
    public DepositRecord(Long orderId, Long productId, Integer paymentPrice, LocalDateTime paymentDate, Long userId, String ci) {
        this.orderId = orderId;
        this.productId = productId;
        this.paymentPrice = paymentPrice;
        this.paymentDate = paymentDate;
        this.userId = userId;
        this.ci = ci;
        this.createdAt = LocalDateTime.now();
    }

}