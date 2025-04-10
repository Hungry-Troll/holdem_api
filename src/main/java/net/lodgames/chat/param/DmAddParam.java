package net.lodgames.chat.param;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DmAddParam {
    @JsonIgnore
    private long userId;
    private Long targetId;
}
