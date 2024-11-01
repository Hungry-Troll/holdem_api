package net.lodgames.currency.repository;

import net.lodgames.currency.model.Coin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CoinRepository extends JpaRepository<Coin, Long> {
    Optional<Coin> findByUserId(long userId);
}
