package net.lodgames.payment.google.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class GooglePayment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "order_id")
    private String orderId;
    @Column(name = "product_id")
    private String productId;
    @Column(name = "google_payment_log")
    private String googlePaymentLog;
    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
