package net.lodgames.chat.param;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import net.lodgames.chat.constant.MsgType;


@Getter
@Setter
public class SendDmMsgParam {
    @JsonIgnore
    private Long senderId;
    private Long targetId;
    private String msgBody;
    private MsgType msgType;
    @JsonIgnore
    private String dest;

}
