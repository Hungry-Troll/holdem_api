package net.lodgames.currency.gold.repository;

import net.lodgames.currency.gold.model.GoldRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoldRecordRepository extends JpaRepository<GoldRecord, Long> {
    boolean existsByIdempotentKey(String idempotentKey);
}
