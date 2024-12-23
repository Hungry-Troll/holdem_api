package net.lodgames.message.param;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageAddParam {
    @JsonIgnore
    private long senderId;
    private long receiverId;
    private String content;
}
