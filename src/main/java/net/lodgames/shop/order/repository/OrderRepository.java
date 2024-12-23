package net.lodgames.shop.order.repository;

import net.lodgames.shop.order.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Orders, Long> {

}
