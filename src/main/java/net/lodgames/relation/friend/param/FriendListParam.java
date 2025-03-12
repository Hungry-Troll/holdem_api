package net.lodgames.relation.friend.param;

import com.fasterxml.jackson.annotation.JsonIgnore;
import net.lodgames.common.param.PagingParam;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FriendListParam extends PagingParam {
    @JsonIgnore
    private long userId;
}
