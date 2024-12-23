package net.lodgames.shop.deposit.repository;

import net.lodgames.shop.deposit.model.DepositRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DepositRecordRepository extends JpaRepository<DepositRecord, Long> {
    Optional<DepositRecord> findByOrderId(Long orderId);
}
