package net.lodgames.user.param;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileAddParam {
    private Long userId;
    private String nickname;
    private String uniqueNickname;
    private String image;
}
