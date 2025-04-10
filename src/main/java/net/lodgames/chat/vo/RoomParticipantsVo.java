package net.lodgames.chat.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import net.lodgames.chat.constant.RoomType;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class RoomParticipantsVo {
    private Long id;
    private RoomType roomType;
    private String name;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String idCode;
    private int capacity;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String secureCode;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer participantCount;
    private LocalDateTime createdAt; // 만든날짜
    private LocalDateTime updatedAt; // 변경일
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LocalDateTime deletedAt; // 삭제일
    private List<ParticipantVo> participantVos;

    public void addParticipantVo(ParticipantVo participantVo) {
        if (participantVos == null) {
            participantVos = new ArrayList<>();
        }
        participantVos.add(participantVo);
    }

}
