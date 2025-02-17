package net.lodgames.relation.friend.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import net.lodgames.user.constants.UserStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class FriendVo {
    // friend
    @JsonIgnore
    private long id;
    // user (userId = friendId)
    private long friendId;
    private UserStatus status;
    // profile(friend)
    private String nickname;
    private String image;
    private Integer basicImageIdx;
    // friend
    private LocalDateTime createdAt; // 만든날짜
    private LocalDateTime updatedAt; // 변경일
}
