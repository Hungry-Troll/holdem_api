package net.lodgames.relation.friend.model;

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
@Entity(name = "friend_block")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class FriendBlock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private Long friendId;

    @LastModifiedDate
    private LocalDateTime updatedAt; // 변경일

    @CreatedDate
    private LocalDateTime createdAt; // 만든날짜

    @Builder
    public FriendBlock(long userId, long friendId){
        this.userId = userId;
        this.friendId = friendId;
    }
}
