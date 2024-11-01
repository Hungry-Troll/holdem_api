package net.lodgames.currency.repository;

import net.lodgames.currency.model.CoinRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoinRecordRepository extends JpaRepository<CoinRecord, Long> {
    boolean existsByIdempotentKey(String idempotentKey);
}
