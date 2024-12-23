package net.lodgames.message.param;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import net.lodgames.common.param.PagingParam;

@Getter
@Setter
public class MessageReceiveBoxParam extends PagingParam {
    @JsonIgnore
    private long receiverId;
}
