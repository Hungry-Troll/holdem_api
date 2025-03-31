package net.lodgames.society.param.member;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import net.lodgames.common.param.PagingParam;

@Getter
@Setter
public class SocietyMemberListParam extends PagingParam {
    @JsonIgnore
    private long userId;
    private long societyId;
}
