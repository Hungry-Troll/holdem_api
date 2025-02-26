package net.lodgames.chat.vo;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class DmSendResultVo {
    private long targetId;
    private LocalDateTime sendTime;

}
