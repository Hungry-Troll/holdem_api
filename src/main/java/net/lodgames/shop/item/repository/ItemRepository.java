package net.lodgames.shop.item.repository;

import net.lodgames.shop.item.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
