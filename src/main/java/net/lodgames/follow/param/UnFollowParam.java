package net.lodgames.follow.param;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
public class UnFollowParam {
    @JsonIgnore
    private long userId;
    private long followId;
}
