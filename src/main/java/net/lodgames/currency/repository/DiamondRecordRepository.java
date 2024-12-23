package net.lodgames.currency.repository;

import net.lodgames.currency.model.DiamondRecord;
import net.lodgames.currency.model.CoinRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiamondRecordRepository extends JpaRepository<DiamondRecord, Long> {
    boolean existsByIdempotentKey(String idempotentKey);
}
