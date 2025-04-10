package net.lodgames.chat.vo;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder

@Getter
@Setter
public class SendResultVo {
    private long destId;
    private LocalDateTime sendTime;

}
