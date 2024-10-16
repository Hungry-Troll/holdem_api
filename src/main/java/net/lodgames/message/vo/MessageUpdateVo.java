package net.lodgames.message.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageUpdateVo {
    private long id;
    private long senderId;
    private long receiverId;
    private String content;
}
