package net.lodgames.currency.repository;

import net.lodgames.currency.model.Diamond;
import net.lodgames.currency.model.Coin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DiamondRepository extends JpaRepository<Diamond, Long> {
    Optional<Diamond> findByUserId(long userId);
}
