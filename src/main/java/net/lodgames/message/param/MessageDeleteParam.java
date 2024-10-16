package net.lodgames.message.param;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import net.lodgames.common.param.PagingParam;

@Getter
@Setter
@Builder
public class MessageDeleteParam {
    private long messageId;
    private long receiverId;
}
