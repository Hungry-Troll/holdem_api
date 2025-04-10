package net.lodgames.society.vo;

import lombok.Getter;
import lombok.Setter;
import net.lodgames.society.constants.MemberType;
import net.lodgames.user.constants.UserStatus;

import java.time.LocalDateTime;

@Getter
@Setter
public class SocietyMemberVo {
    private Long id;
    private Long userId;
    private Long societyId;
    private UserStatus userStatus;
    private MemberType memberType;
    private LocalDateTime createdAt;
    private String nickname;
    private String image;
    private Integer basicImageIdx;
}
