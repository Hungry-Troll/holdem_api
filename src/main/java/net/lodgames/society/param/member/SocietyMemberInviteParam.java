package net.lodgames.society.param.member;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class SocietyMemberInviteParam {
    @JsonIgnore
    private long userId;    // 유저 고유번호 (초대하는 유저)
    private long societyId; // 모임 고유번호
    private long memberId;  // 초대할 유저 고유번호

}
