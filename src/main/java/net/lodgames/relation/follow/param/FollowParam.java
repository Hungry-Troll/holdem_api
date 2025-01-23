package net.lodgames.relation.follow.param;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FollowParam {
    @JsonIgnore
    private long userId;
    private long followId;
}
