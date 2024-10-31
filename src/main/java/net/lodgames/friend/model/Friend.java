package net.lodgames.friend.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Entity(name = "friend")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Friend {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private Long friendId;

    private Integer type;

    @LastModifiedDate
    private LocalDateTime updatedAt; // 변경일

    @CreatedDate
    private LocalDateTime createdAt; // 만든날짜

    @Builder
    public Friend(long userId, long friendId, Integer type) {
        this.type = type;
        this.userId = userId;
        this.friendId = friendId;
    }
}
