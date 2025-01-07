package net.lodgames.message.param;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageModParam {
    @JsonIgnore
    private long messageId;
    @JsonIgnore
    private long senderId;
    private String content;
}
