package net.lodgames.storage.model;

import jakarta.persistence.*;
import lombok.*;
import net.lodgames.storage.constants.StorageCurrencyType;
import net.lodgames.storage.constants.StorageSenderType;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)
public class StorageCurrency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "storage_id", nullable = false)
    private Long storageId;
    @Column(name = "currency_amount", nullable = false)
    private Long currencyAmount; // 재화량
    @Column(name = "currency_type", nullable = false)
    private StorageCurrencyType currencyType;
    @CreatedDate
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
}
