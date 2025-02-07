package net.lodgames.stamina.vo;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class StaminaAcquireVo {
    private Integer currentStamina;
    private Integer maxStamina;
    private LocalDateTime lastRecoveryTime;
    private LocalDateTime recoveryCompleteTime;
}
