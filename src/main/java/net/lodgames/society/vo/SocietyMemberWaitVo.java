package net.lodgames.society.vo;

import lombok.Getter;
import lombok.Setter;
import net.lodgames.user.constants.UserStatus;

import java.time.LocalDateTime;

@Getter
@Setter
public class SocietyMemberWaitVo {
    private Long id;
    private Long userId;
    private Long societyId;
    private UserStatus status;
    private LocalDateTime createdAt;
    private String nickname;
    private String image;
    private Integer basicImageIdx;
}
