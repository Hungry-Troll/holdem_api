package net.lodgames.user.param;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInitParam {
    @JsonIgnore
    private long userId;
    private String nickName;
}
