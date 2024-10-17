package net.lodgames.friend.param;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class FriendInfoParam {
    @JsonIgnore
    private long userId;
    private long friendId;
}
