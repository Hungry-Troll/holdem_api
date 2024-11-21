package net.lodgames.user.vo;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ProfileVo {
    private Long id;
    private Long userId;
    private String nickname;
    private String image;
    private Short basicImageIdx;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
