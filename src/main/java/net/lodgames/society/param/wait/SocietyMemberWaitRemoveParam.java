package net.lodgames.society.param.wait;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SocietyMemberWaitRemoveParam {
    @JsonIgnore
    private long userId;
    private long societyId;
    private long memberId;
}
