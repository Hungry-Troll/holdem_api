package net.lodgames.user.param;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileModParam {
    private Long userId;
    private String nickname;
    private String image;
    private Short basicImageIdx;
}
