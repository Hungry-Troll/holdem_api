package net.lodgames.society.param.member;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SocietyMemberAllowParam {
    @JsonIgnore
    private long userId;    // 모입 가입을 허락할 유저 고유번호
    private long societyId; // 가입 모임 고유번호
    private long memberId;  // 가입 허락을 기다리는 유저 고유번호
}
