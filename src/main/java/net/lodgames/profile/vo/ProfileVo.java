package net.lodgames.profile.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileVo {
    @JsonIgnore
    private long id;
    // 유저
    private long userId;
    // 프로필
    private String nickname;
    private String image;
    private Short basicImageIdx;
}
