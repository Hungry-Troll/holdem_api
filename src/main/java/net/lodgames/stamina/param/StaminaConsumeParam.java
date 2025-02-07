package net.lodgames.stamina.param;

import com.fasterxml.jackson.annotation.JsonIgnore;
import net.lodgames.stamina.constants.ConsumeType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StaminaConsumeParam {
    @JsonIgnore
    private Long userId;
    private ConsumeType consumeType;
}
