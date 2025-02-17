package net.lodgames.user.vo;

import lombok.Getter;
import lombok.Setter;
import net.lodgames.user.constants.UserStatus;

import java.time.LocalDateTime;

@Getter
@Setter
public class FindUserNicknameVo {
    // 유저
    private Long userId;
    private UserStatus status;
    private LocalDateTime logoutAt;
    // 프로필
    private String nickname;
    private String image;
    private Integer basicImageIdx;
}
