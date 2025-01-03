package net.lodgames.currency.diamond.repository;

import net.lodgames.currency.diamond.model.Diamond;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DiamondRepository extends JpaRepository<Diamond, Long> {
    Optional<Diamond> findByUserId(long userId);
}
