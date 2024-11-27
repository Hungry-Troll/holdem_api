package net.lodgames.board.vo;

import lombok.Getter;
import lombok.Setter;
import net.lodgames.board.constants.BoardStatus;
import net.lodgames.board.constants.BoardType;

import java.time.LocalDateTime;

@Getter
@Setter
public class BoardVo {
    private int id;
    private String title;
    private String content;
    private BoardType boardType;
    private BoardStatus status;
    private String image;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
