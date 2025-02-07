package net.lodgames.stamina.vo;

import lombok.Builder;
import lombok.Getter;


import java.time.LocalDateTime;

@Getter
@Builder
public class StaminaCalculateVo {
    private int currentStamina;
    private int maxStamina;
    private LocalDateTime recoveryCompleteTime;
    private LocalDateTime now;
}
