package net.lodgames.relation.follow.vo;

import lombok.Getter;
import lombok.Setter;
import net.lodgames.user.constants.UserStatus;

import java.time.LocalDateTime;

@Getter
@Setter
public class FollowVo {
    private long id;
    private long userId;
    private long followId;
    private LocalDateTime createdAt;
    private UserStatus status;
    private String nickname;
    private String image;
}
