package net.lodgames.chat.vo;

import lombok.Getter;
import lombok.Setter;
import net.lodgames.chat.constant.DestType;

import java.time.LocalDateTime;

@Getter
@Setter
public class MsgVo {
    private DestType destType;
    private String dest;
    private long senderId;
    private String msgType;
    private String msgBody;
    private LocalDateTime sendTime;
}

