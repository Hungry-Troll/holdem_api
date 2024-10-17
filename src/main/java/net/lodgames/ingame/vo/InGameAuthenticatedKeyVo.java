package net.lodgames.ingame.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class InGameAuthenticatedKeyVo {
    @JsonProperty("value")
    private String value;
}
