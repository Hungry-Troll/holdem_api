package net.lodgames.currency.coin.repository;

import net.lodgames.currency.coin.model.CoinRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoinRecordRepository extends JpaRepository<CoinRecord, Long> {
    boolean existsByIdempotentKey(String idempotentKey);
}
