package net.lodgames.relation.friend.param;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import net.lodgames.common.param.PagingParam;

@Getter
@Setter
public class FriendSearchParam extends PagingParam {
    @JsonIgnore
    private Long userId;
    private String nickname;
}
