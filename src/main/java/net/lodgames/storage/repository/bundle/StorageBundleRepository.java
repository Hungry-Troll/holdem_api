package net.lodgames.storage.repository.bundle;

import net.lodgames.storage.model.StorageBundle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StorageBundleRepository extends JpaRepository<StorageBundle, Long> {
    Optional<StorageBundle> findByStorageId(Long storageId);
}
