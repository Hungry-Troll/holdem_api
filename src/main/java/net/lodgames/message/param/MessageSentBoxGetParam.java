package net.lodgames.message.param;

import lombok.Getter;
import lombok.Setter;
import net.lodgames.common.param.PagingParam;

@Getter
@Setter
public class MessageSentBoxGetParam extends PagingParam {
    private long id;
    private long senderId;
}
