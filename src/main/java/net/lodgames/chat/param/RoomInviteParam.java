package net.lodgames.chat.param;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RoomInviteParam {
    @JsonIgnore
    private long userId;
    private long roomId;
    private List<Long> inviteList;
}
