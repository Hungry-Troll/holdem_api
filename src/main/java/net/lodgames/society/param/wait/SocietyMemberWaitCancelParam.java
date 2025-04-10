package net.lodgames.society.param.wait;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SocietyMemberWaitCancelParam {
    @JsonIgnore
    private long userId;
    private long societyId;
}
