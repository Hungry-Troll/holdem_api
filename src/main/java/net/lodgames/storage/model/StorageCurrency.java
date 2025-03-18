package net.lodgames.storage.model;

import jakarta.persistence.*;
import lombok.*;
import net.lodgames.currency.common.constants.CurrencyType;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "storage_currency")
@EntityListeners(AuditingEntityListener.class)
public class StorageCurrency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "storage_id")
    private Long storageId;
    @Column(name = "currency_amount")
    private Long currencyAmount; // 재화량
    @Column(name = "currency_type")
    private CurrencyType currencyType;
    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
