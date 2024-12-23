package net.lodgames.shop.deposit.repository;

import net.lodgames.shop.deposit.model.DepositCurrencyRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepositCurrencyRecordRepository extends JpaRepository<DepositCurrencyRecord, Long> {
    void deleteByDepositRecordId(Long orderId);
}
