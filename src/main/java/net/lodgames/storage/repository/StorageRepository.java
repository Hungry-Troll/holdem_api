package net.lodgames.storage.repository;

import net.lodgames.storage.model.Storage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StorageRepository extends JpaRepository<Storage, Long> {
    Optional<List<Storage>> findByReceiverId(Long aLong);
}
