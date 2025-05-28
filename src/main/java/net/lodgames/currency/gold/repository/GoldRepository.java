package net.lodgames.currency.gold.repository;

import net.lodgames.currency.gold.model.Gold;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GoldRepository extends JpaRepository<Gold, Long> {
    Optional<Gold> findByUserId(long userId);
    boolean existsByUserId(long userId);
}
