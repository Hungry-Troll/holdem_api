package net.lodgames.stamina.param;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class StaminaModParam {
    @JsonIgnore
    private Long userId;
    private Integer currentStamina;
    private Integer maxStamina;
    private LocalDateTime lastRecoveryTime;
    private LocalDateTime recoveryCompleteTime;
}
