package net.lodgames.friend.vo;

import net.lodgames.user.user.constants.UserStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class FriendBlockVo {
    @JsonIgnore
    private long id;
    @JsonIgnore
    private long userId;
    private long friendId;
    private LocalDateTime createdAt;
    private UserStatus status;
    private String nickname;
    private String image;
}
