package net.lodgames.storage.model;

import jakarta.persistence.*;
import lombok.*;
import net.lodgames.storage.constants.StorageSenderType;
import net.lodgames.storage.constants.StorageStatus;
import net.lodgames.storage.constants.StorageContentType;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Storage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "receiver_id", nullable = false)
    private Long receiverId; // 받은 유저
    @Column(name = "sender_id")
    private Long senderId; // 보낸 유저
    @Column(name ="purchase_id")
    private Long purchaseId; // 구매 아이디 (아이템 / 번들이 콜렉션으로 들어갈 때 기록 장부)
    @Column(name = "title")
    private String title; // 제목
    @Column(name = "description", length = 255)
    private String description; // 내용
    @Column(name = "sender_type", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private StorageSenderType senderType; // 전송 타입 (USER (0), ADMIN(1))
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private StorageStatus status; // 상태 ( WAITING (0) : 수령 전, RECEIVED (1) : 수령 후)
    @Column(name = "content_type", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private StorageContentType contentType; // 분류 (CURRENCY (0), Item(1), Bundle(2))
    @Column(name = "expiry_date")
    private LocalDateTime expiryDate; // 기한
    @Column(name = "read_at")
    private LocalDateTime readAt; // 읽은 시각
    @CreatedDate
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt; // 만든 시각
    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt; // 변경 시각
    @Column(name ="deleted_at")
    private LocalDateTime deletedAt; // 삭제 시각
}
