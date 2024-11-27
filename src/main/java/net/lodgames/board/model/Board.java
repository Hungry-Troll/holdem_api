package net.lodgames.board.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.lodgames.board.constants.BoardStatus;
import net.lodgames.board.constants.BoardType;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Data
@Entity(name = "board")
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "board_type")
    private BoardType boardType;

    @Column(name = "status")
    private BoardStatus status;

    @Column(name = "image")
    private String image;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Builder
    public Board(String title, String content, BoardType boardType, BoardStatus status, String image, String link, LocalDateTime startDate, LocalDateTime endDate) {
        this.title = title;
        this.content = content;
        this.boardType = boardType;
        this.status = status;
        this.image = image;
    }
}
