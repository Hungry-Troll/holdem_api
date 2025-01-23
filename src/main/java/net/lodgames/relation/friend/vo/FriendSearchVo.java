package net.lodgames.relation.friend.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import net.lodgames.user.constants.UserStatus;

import java.time.LocalDateTime;

@Getter
@Setter
public class FriendSearchVo {
    // Friend
    @JsonIgnore
    private Long id;
    private Long friendId;
    // User
    private UserStatus status;
    private LocalDateTime logoutAt;
    // Profile
    private String nickname;
    private String image;
    private Short basicImageIdx;

}
