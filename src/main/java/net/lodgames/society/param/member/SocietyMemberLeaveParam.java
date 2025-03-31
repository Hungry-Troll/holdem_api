package net.lodgames.society.param.member;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SocietyMemberLeaveParam {
    @JsonIgnore
    private long userId;
    private long societyId;
}
