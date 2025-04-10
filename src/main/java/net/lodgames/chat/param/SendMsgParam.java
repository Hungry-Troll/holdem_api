package net.lodgames.chat.param;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import net.lodgames.chat.constant.MsgType;


@Getter
@Setter
public class SendMsgParam {
    @JsonIgnore
    private Long senderId;
    private Long destId;
    private String msgBody;
    private MsgType msgType;
}