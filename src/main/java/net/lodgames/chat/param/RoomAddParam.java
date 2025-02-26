package net.lodgames.chat.param;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import net.lodgames.chat.constant.RoomType;

@Getter
@Setter
public class RoomAddParam {
    @JsonIgnore
    private long userId;
    private String name;
    private RoomType roomType;
    private String secureCode;
}
