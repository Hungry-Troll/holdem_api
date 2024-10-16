package net.lodgames.message.param;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageUpdateParam {
    private long messageId;
    private long senderId;
    private long receiverId;
    private String content;
}
