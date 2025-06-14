package net.lodgames.stamina.vo;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class StaminaVo {
    private int currentStamina;
    private int maxStamina;
    private LocalDateTime lastRecoveryTime;
    private LocalDateTime recoveryCompleteTime;
}
