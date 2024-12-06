package net.lodgames.storage.repository.currency;

import net.lodgames.storage.model.StorageCurrency;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StorageCurrencyRepository extends JpaRepository<StorageCurrency, Long> {
    Optional<StorageCurrency> findByStorageId(Long storageId);
}
