package net.lodgames.society.param.member;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SocietyMemberAcceptParam {
    @JsonIgnore
    private long userId;    // 초대 수락 유저 고유번호
    private long societyId; // 해당 모임 아이디
}
