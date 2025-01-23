package net.lodgames.profile.param;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProfileModParam {
    private Long userId;
    private String nickname;
    private String image;
    private Short basicImageIdx;
}
