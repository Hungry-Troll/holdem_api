package net.lodgames.relation.friend.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Entity(name = "friend_request")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class FriendRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long receiver;

    private Long sender;

    @CreatedDate
    private LocalDateTime createdAt; // 만든날짜

    @Builder
    public FriendRequest(long receiver, long sender){
        this.receiver = receiver;
        this.sender = sender;
    }
}
