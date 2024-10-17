package net.lodgames.friend.param;

import net.lodgames.common.param.PagingParam;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FindFriendParam extends PagingParam {
    @JsonIgnore
    private long userId;
    private String id;
    private String nickname;
}
