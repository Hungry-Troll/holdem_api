package net.lodgames.stamina.param;

import lombok.Getter;
import lombok.Setter;
import net.lodgames.stamina.constants.AcquireType;

@Getter
@Setter
public class StaminaAcquireParam {
    private Long userId;
    private AcquireType acquireType;
}
