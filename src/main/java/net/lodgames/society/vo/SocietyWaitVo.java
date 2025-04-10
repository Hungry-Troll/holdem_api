package net.lodgames.society.vo;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class SocietyWaitVo {
    private Long societyId;
    private String name;
    private String nickname;
    private LocalDateTime createdAt;
}

