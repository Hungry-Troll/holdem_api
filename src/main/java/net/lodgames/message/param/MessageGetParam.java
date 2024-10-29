package net.lodgames.message.param;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MessageGetParam {
    private long messageId;
    private long receiverId;
}
