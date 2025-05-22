package net.lodgames.relation.friend.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoneFriendInfoVo {
    // user(none friend)
    // none friend userId
    private long userId;
    // profile(none friend)
    private String image;
    private String nickname;
    private int basicImageIdx;
    private boolean isRequest;
}
