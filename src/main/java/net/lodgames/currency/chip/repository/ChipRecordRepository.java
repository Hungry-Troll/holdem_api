package net.lodgames.currency.chip.repository;

import net.lodgames.currency.chip.model.ChipRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChipRecordRepository extends JpaRepository<ChipRecord, Long> {
    boolean existsByIdempotentKey(String idempotentKey);
}
