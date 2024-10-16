package net.lodgames.message.vo;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class MessageReadVo {
    private long id;
    private long senderId;
    private long receiverId;
    private String content;
    private LocalDateTime readAt;
}
