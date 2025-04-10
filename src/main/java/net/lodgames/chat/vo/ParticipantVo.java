package net.lodgames.chat.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import net.lodgames.chat.constant.ParticipantType;

import java.time.LocalDateTime;

@Getter
@Setter
public class ParticipantVo {
    @JsonIgnore
    private Long id;
    private Long roomId;
    private Long userId;
    private ParticipantType participantType;
    private LocalDateTime createdAt; // 만든날짜
    private LocalDateTime updatedAt; // 변경일
}
