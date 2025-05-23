package net.lodgames.message.vo;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class MessageBoxVo {
    // Message
    private long id;
    private long senderId;
    private long receiverId;
    private String content;
    private boolean isRead;
    // Profile
    private String nickname;
    private String image;
    private int basicImageIdx;
    // Message
    private LocalDateTime createdAt;
}
