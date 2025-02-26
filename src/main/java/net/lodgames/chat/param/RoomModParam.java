package net.lodgames.chat.param;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import net.lodgames.chat.constant.RoomType;

@Getter
@Setter
public class RoomModParam {
    @JsonIgnore
    private Long userId;
    private Long roomId;
    private RoomType roomType;
    private String secureCode;
    private String name;
    private Integer capacity;
}
