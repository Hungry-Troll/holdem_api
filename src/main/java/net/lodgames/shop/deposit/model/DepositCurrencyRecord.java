package net.lodgames.shop.deposit.model;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.lodgames.currency.common.constants.CurrencyType;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DepositCurrencyRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                   // 입금재화기록 고유번호
    private Long depositRecordId;      // 입금기록 고유번호
    private CurrencyType currencyType; // 재화타입
    private Long depositAmount;        // 입금금액
    private String idempotentKey;      // 멱등키
    private LocalDateTime createdAt;   // 생성시각
    @Builder
    public DepositCurrencyRecord(Long depositRecordId, CurrencyType currencyType, Long depositAmount, String idempotentKey) {
        this.depositRecordId = depositRecordId;
        this.currencyType = currencyType;
        this.depositAmount = depositAmount;
        this.idempotentKey = idempotentKey;
        this.createdAt = LocalDateTime.now();
    }
}