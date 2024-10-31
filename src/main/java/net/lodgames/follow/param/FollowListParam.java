package net.lodgames.follow.param;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import net.lodgames.common.param.PagingParam;
import lombok.Getter;

@Getter
@Builder
public class FollowListParam extends PagingParam {
    @JsonIgnore
    private long userId;
}
