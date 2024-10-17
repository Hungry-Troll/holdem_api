package net.lodgames.ingame.param;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InGameAuthenticationKeyParam {
    @JsonProperty("authenticationKey")
    private String authenticationKey;
}
