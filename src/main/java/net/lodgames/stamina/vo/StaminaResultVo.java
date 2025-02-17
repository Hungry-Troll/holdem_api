package net.lodgames.stamina.vo;

import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Builder
@Getter
public class StaminaResultVo {
    private int currentStamina;
    private int maxStamina;
    private Instant recoveryCompleteTime;
}
