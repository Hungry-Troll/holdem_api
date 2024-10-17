package net.lodgames.friend.vo;

import net.lodgames.user.constants.UserStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class FriendInfoVo {

    // account(friend)
    private String id;
    private UserStatus status;

    // profile(friend)
    private String image;
    private String nickname;

    // friend
    private long friendId;
    private LocalDateTime createdAt; // 만든날짜
    private LocalDateTime updatedAt; // 변경일
}
