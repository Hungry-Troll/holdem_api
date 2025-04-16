package net.lodgames.message.param;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageAddParam {
    @JsonIgnore
    private Long senderId;
    private Long receiverId;
    private String content;
}
