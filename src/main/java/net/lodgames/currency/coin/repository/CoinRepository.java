package net.lodgames.currency.coin.repository;

import net.lodgames.currency.coin.model.Coin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CoinRepository extends JpaRepository<Coin, Long> {
    Optional<Coin> findByUserId(long userId);
    boolean existsByUserId(long userId);
}
