package net.lodgames.message.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class MessageReadVo {
    @JsonIgnore
    private long id;
    private long senderId;
    private long receiverId;
    private String content;
    @JsonIgnore
    private LocalDateTime readAt;
}
