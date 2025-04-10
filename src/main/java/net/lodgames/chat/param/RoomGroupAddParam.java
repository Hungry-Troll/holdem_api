package net.lodgames.chat.param;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import net.lodgames.chat.constant.RoomType;

import java.util.List;

@Getter
@Setter
public class RoomGroupAddParam {
    @JsonIgnore
    private long userId;
    private String name;
    private RoomType roomType;
    private List<Long> inviteList;
    private String secureCode;
}
