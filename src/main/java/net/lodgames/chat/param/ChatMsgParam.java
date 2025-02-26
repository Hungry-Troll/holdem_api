package net.lodgames.chat.param;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import net.lodgames.common.param.PagingParam;

import java.time.LocalDateTime;

@Getter
@Setter
public class ChatMsgParam extends PagingParam {
    @JsonIgnore
    private Long userId;
    private String dest;
    private LocalDateTime lastCheckTime;
}
