package net.lodgames.currency.chip.repository;

import net.lodgames.currency.chip.model.Chip;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChipRepository extends JpaRepository<Chip, Long> {
    Optional<Chip> findByUserId(long userId);
}
