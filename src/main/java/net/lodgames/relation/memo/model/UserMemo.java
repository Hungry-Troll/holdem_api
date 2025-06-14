package net.lodgames.relation.memo.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity(name = "user_memo")
@Getter
@Setter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class UserMemo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_id") // 메모 작성한 유저의 id
    private Long userId;
    @Column(name = "target_user_id") // 메모 대상 유저의 id
    private Long targetUserId;
    @Column(name = "memo_text")
    private String memoText;
    private String tag;
    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Builder
    public UserMemo(Long userId, Long targetUserId, String memoText, String tag) {
        this.userId = userId;
        this.targetUserId = targetUserId;
        this.memoText = memoText;
        this.tag = tag;
    }
}
