package net.lodgames.chat.param;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import net.lodgames.chat.constant.LeaveStatus;
import net.lodgames.common.param.PagingParam;

import java.time.LocalDateTime;


@Getter
@Setter
public class DmListParam extends PagingParam {
    @JsonIgnore
    private long userId;
    private LocalDateTime lastCheckTime;
    private LeaveStatus leaveStatus;
}
