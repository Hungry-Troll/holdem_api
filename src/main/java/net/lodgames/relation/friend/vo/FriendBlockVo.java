package net.lodgames.relation.friend.vo;

import net.lodgames.user.constants.UserStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class FriendBlockVo {
    @JsonIgnore
    private long id;

    // friend_block
    @JsonIgnore
    private long userId;
    private long friendId;
    private LocalDateTime createdAt;

    // account
    private UserStatus status;

    // profile
    private String nickname;
    private String image;
    private Integer basicImageIdx;
}
