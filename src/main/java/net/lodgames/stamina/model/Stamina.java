package net.lodgames.stamina.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Slf4j
@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Stamina {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "current_stamina")
    private Integer currentStamina;
    @Column(name = "max_stamina")
    private Integer maxStamina;
    @Column(name = "last_recovery_time")
    private LocalDateTime lastRecoveryTime;// 스태미나가 완전히 회복되기까지 남은 시간을 계산하기 위한 서버 시간
    @Column(name =" recovery_complete_time")
    private LocalDateTime recoveryCompleteTime; // 스태미나가 완전히 회복되기까지 남은 시간
    @Column(name = "updated_at")
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Builder
    public Stamina(Long userId, Integer currentStamina, Integer maxStamina,LocalDateTime lastRecoveryTime ,LocalDateTime recoveryCompleteTime) {
        this.userId = userId;
        this.currentStamina = currentStamina;
        this.maxStamina = maxStamina;
        this.lastRecoveryTime = lastRecoveryTime;
        this.recoveryCompleteTime = recoveryCompleteTime;
    }
}
