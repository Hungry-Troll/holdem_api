package net.lodgames.shop.payment.repository;

import net.lodgames.shop.payment.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
