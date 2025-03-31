package net.lodgames.society.param.wait;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import net.lodgames.common.param.PagingParam;
import net.lodgames.society.constants.WaitType;

@Getter
@Setter
public class SocietyOwnWaitListParam extends PagingParam {
    @JsonIgnore
    private long userId;
    @JsonIgnore
    private WaitType waitType;

}
