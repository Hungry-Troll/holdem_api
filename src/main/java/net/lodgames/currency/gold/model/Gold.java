package net.lodgames.currency.gold.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;


@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "gold")
@EntityListeners(AuditingEntityListener.class)
@Builder
public class Gold {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "amount")
    private Long amount;

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdAt; // 만든날짜

    @LastModifiedDate
    private LocalDateTime updatedAt; // 변경일


    public void changeAmount(long newAmount) {
        this.amount = newAmount;
    }

    public void addAmount(long addAmount) {
        this.amount += addAmount;
    }

    public void deductAmount(long deductAmount) {
        this.amount -= deductAmount;
    }

}
