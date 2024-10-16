package net.lodgames.message.param;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import net.lodgames.common.param.PagingParam;

@Getter
@Setter
@Builder
public class MessageReceivedBoxGetParam extends PagingParam {
    private long id;
    private long receiverId;
}
