package net.lodgames.friend.param;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class FriendRequestParam {
    private long sender;
    private long receiver;
}
