package net.lodgames.chat.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import net.lodgames.chat.constant.LeaveStatus;
import net.lodgames.chat.constant.TargetStatus;

import java.time.LocalDateTime;

@Getter
@Setter
public class DmVo {
    @JsonIgnore
    private Long id;
    @JsonIgnore
    private Long userId;
    private Long targetId;
    private TargetStatus targetStatus;
    private LeaveStatus leaveStatus;
    // private Long lastId;
    private LocalDateTime createdAt; // 만든날짜
    private LocalDateTime updatedAt; // 변경일
}
