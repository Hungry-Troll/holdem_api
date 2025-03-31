package net.lodgames.society.param;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import net.lodgames.common.param.PagingParam;
import net.lodgames.society.constants.MemberType;

@Getter
@Setter
public class SocietyListParam extends PagingParam {
    @JsonIgnore
    private long userId;
    private MemberType memberType;
}
