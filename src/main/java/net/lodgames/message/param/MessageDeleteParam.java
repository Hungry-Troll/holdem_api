package net.lodgames.message.param;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MessageDeleteParam {
    private Long messageId;
    private Long receiverId;
}
