package net.lodgames.chat.vo;

import lombok.Getter;
import lombok.Setter;
import net.lodgames.chat.constant.LeaveStatus;
import net.lodgames.chat.constant.TargetStatus;

@Getter
@Setter
public class ChatDmStatusVo {
    private LeaveStatus leaveStatus;
    private TargetStatus targetStatus;
}
