package net.lodgames.currency.diamond.repository;

import net.lodgames.currency.diamond.model.DiamondRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiamondRecordRepository extends JpaRepository<DiamondRecord, Long> {
    boolean existsByIdempotentKey(String idempotentKey);
}
