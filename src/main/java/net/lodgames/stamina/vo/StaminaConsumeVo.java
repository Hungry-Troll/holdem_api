package net.lodgames.stamina.vo;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class StaminaConsumeVo {
    private Integer currentStamina;
    private Integer maxStamina;
    private LocalDateTime lastRecoveryTime;
    private LocalDateTime recoveryCompleteTime;
}
