package net.lodgames.shop.order.model;

import jakarta.persistence.*;
import lombok.*;
import net.lodgames.shop.order.constants.OrderStatus;
import net.lodgames.user.constants.Os;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long productId;
    private Long userId;
    private Os os;
    private Integer paymentPrice;
    private Integer originPrice;
    private OrderStatus orderStatus;
    private LocalDateTime paymentDate;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;
}