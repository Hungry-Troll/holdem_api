package net.lodgames.message.vo;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class MessageDeleteVo {
    private Long id;
    private Long senderId;
    private Long receiverId;
    private LocalDateTime deletedAt;
}
