package net.lodgames.chat.model;

import jakarta.persistence.*;
import lombok.*;
import net.lodgames.chat.constant.RoomType;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@Entity(name = "chat_room")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;            // 고유번호
    private String idCode;      // ID 코드 ( 임의로 생성된 구분 문자열 )
    private RoomType roomType;  // 방타입
    private String name;        // 이름
    private int capacity;       // 정원
    private int currentUserNum; // 현재인원
    private String secureCode;  // RoomType SECURE 일때 확인 코드
    @CreatedDate
    private LocalDateTime createdAt; // 만든 날짜
    @LastModifiedDate
    private LocalDateTime updatedAt; // 변경일
    private LocalDateTime deletedAt; // 삭제 날짜

}
