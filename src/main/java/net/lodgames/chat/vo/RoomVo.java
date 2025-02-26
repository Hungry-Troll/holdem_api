package net.lodgames.chat.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import net.lodgames.chat.constant.RoomType;

import java.time.LocalDateTime;

@Getter
@Setter
public class RoomVo {
    private Long id;
    private RoomType roomType;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String idCode;
    private String name;
    private int capacity;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String secureCode;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer participantCount;
    private LocalDateTime createdAt; // 만든날짜
    private LocalDateTime updatedAt; // 변경일
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LocalDateTime deletedAt; // 삭제일
}
