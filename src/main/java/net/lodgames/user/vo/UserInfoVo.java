package net.lodgames.user.vo;

import lombok.Getter;
import lombok.Setter;
import net.lodgames.user.constants.UserStatus;

@Getter
@Setter
public class UserInfoVo {
    // account(User)
    private long userId;
    private UserStatus status;
    // profile(friend)
    private String image;
    private String nickname;
    private Integer basicImageIdx;
    // follow
    private Boolean isFollow;
    // userBlock
    private Boolean isBlock;
    // userMemo
    private String memoText;
    private String tag;
}
