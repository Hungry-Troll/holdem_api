package net.lodgames.message.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity(name = "message")
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name ="sender_id")
    private Long senderId;
    @Column(name ="receiver_id")
    private Long receiverId;
    private String content;
    @CreatedDate
    @Column(name ="created_at")
    private LocalDateTime createdAt;
    @Column(name ="read_at")
    private LocalDateTime readAt;
    @Column(name ="deleted_at")
    private LocalDateTime deletedAt;

    @Builder
    public Message(Long senderId, Long receiverId, String content) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.content = content;
    }
}
