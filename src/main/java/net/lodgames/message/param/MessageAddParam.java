package net.lodgames.message.param;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageAddParam {
    private long senderId;
    private long receiverId;
    private String content;
}
