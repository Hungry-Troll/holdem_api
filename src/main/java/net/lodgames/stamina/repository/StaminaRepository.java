package net.lodgames.stamina.repository;

import net.lodgames.stamina.model.Stamina;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StaminaRepository extends JpaRepository<Stamina, Long> {
    Optional<Stamina> findByUserId(Long userId);
    Boolean existsByUserId(Long userId);
}
