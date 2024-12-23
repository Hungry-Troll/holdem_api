package net.lodgames.shop.purchase.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.lodgames.currency.constants.CurrencyType;
import net.lodgames.shop.purchase.constants.PurchaseType;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "purchase")
@Builder
@EntityListeners(AuditingEntityListener.class) // Add this line
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                   // 구매 고유번호
    private Long bundleId;             // 번둘 고유번호
    private Long itemId;               // 아이템 고유번호
    private Long userId;               // 유저 고유번호
    private PurchaseType purchaseType; // 구매타입
    private CurrencyType currencyType; // 재화타입
    private Integer paidAmount;        // 구매금액
    private LocalDateTime canceledAt;  // 취소날짜
    @CreatedDate
    private LocalDateTime createdAt;  // 만든날짜

}