package net.lodgames.relation.block.model;

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
@Entity(name = "user_block")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class UserBlock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_id") // 유저 아이디
    private Long userId;
    @Column(name = "block_user_id") // 차단 유저 아이디
    private Long blockUserId;
    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt; // 만든날짜
    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt; // 변경일


    @Builder
    public UserBlock(Long userId, Long blockId) {
        this.userId = userId;
        this.blockUserId = blockId;
    }
}
