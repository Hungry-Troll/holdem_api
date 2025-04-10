package net.lodgames.chat.model;

import jakarta.persistence.*;
import lombok.*;
import net.lodgames.chat.constant.LeaveStatus;
import net.lodgames.chat.constant.TargetStatus;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@Entity(name = "chat_dm")
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class ChatDm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private Long targetId;
    private LeaveStatus leaveStatus;
    private TargetStatus targetStatus;
    @CreatedDate
    private LocalDateTime createdAt; // 만든날짜
    @LastModifiedDate
    private LocalDateTime updatedAt; // 변경일

}
