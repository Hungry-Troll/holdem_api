package net.lodgames.user.userblock.vo;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserBlockListVo {
    // 차단 유저
    private long blockUserId;
    // 유저
    private LocalDateTime logoutAt;
    // 프로필
    private String nickname;
    private String image;
    private Short basicImageIdx;
}
