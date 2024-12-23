package net.lodgames.shop.collection.repository;

import net.lodgames.shop.collection.model.Collection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CollectionRepository extends JpaRepository<Collection, Long> {
    Optional<Collection> findByIdAndUserId(Long id, Long userId);
}
