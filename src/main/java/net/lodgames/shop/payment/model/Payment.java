package net.lodgames.shop.payment.model;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                 // 결제고유번호
    private Long orderId;            // 주문고유번호
    private Long userId;             // 유저고유번호
    private Integer amount;          // 결제금액
    private String method;           // 결제수단
    @CreatedDate
    private LocalDateTime createdAt; // 만든날짜
    @LastModifiedDate
    private LocalDateTime updatedAt; // 변경일

    @Builder
    public Payment(Long orderId, Long userId, Integer amount, String method) {
        this.orderId = orderId;
        this.userId = userId;
        this.amount = amount;
        this.method = method;
    }
}
