package net.lodgames.storage.repository.item;

import net.lodgames.storage.model.StorageItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StorageItemRepository extends JpaRepository<StorageItem, Long> {
    Optional<StorageItem> findByStorageId(Long storageId);
}
