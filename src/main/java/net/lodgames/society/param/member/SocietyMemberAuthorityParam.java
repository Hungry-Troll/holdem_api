package net.lodgames.society.param.member;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import net.lodgames.society.constants.MemberType;

@Getter
@Setter
public class SocietyMemberAuthorityParam {
    @JsonIgnore
    private long userId;
    private long societyId;
    private long memberId;
    private MemberType memberType;
}
