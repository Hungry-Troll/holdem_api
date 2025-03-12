package net.lodgames.relation.follow.param;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UnFollowParam {
    @JsonIgnore
    private long userId;
    private long followId;
}
