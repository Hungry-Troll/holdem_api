package net.lodgames.friend.param;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class FriendDeleteParam {
    @JsonIgnore
    private long userId;
    private long friendId;
}
