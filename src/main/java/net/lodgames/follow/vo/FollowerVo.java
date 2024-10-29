package net.lodgames.follow.vo;

import lombok.Getter;
import lombok.Setter;
import net.lodgames.user.constants.UserStatus;

import java.time.LocalDateTime;

@Getter
@Setter
public class FollowerVo {
    private long id;
    private long followerId;    // userId
    private long ownId;         // followId
    private LocalDateTime createdAt;
    private UserStatus status;
    private String nickname;
    private String image;
}
