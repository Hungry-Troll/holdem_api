package net.lodgames.shop.bundle.model;

import jakarta.persistence.*;
import lombok.*;
import net.lodgames.currency.common.constants.CurrencyType;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class) // Add this line
public class BundleCurrency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long bundleId;              // 번들 고유 번호
    private CurrencyType currencyType;  // 재화타입 // COIN Only
    private Long count;              // 갯수
    @CreatedDate
    private LocalDateTime createdAt;    // 만든날짜
}