package net.lodgames.payment.google.repository;

import net.lodgames.payment.google.model.GooglePayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GooglePaymentRepository extends JpaRepository<GooglePayment,Long> {
    Optional<GooglePayment> findByUserId(Long userId);
    Optional<GooglePayment> findByOrderId(String orderId);
}
