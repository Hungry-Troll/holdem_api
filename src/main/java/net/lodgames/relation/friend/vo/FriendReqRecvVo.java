package net.lodgames.relation.friend.vo;

import net.lodgames.user.constants.UserStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class FriendReqRecvVo {

    @JsonIgnore
    private long userId;

    // friend_request
    @JsonIgnore
    private long id;
    private long sender;
    private LocalDateTime createdAt; // 만든날짜

    // account
    private String loginId;
    private UserStatus status;

    // profile
    private String nickname;
    private String image;
    private Integer basicImageIdx;
}
