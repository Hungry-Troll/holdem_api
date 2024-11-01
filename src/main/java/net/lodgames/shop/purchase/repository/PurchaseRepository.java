package net.lodgames.shop.purchase.repository;

import net.lodgames.shop.purchase.model.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
}
