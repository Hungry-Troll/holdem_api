package net.lodgames.inquiry.model;

import jakarta.persistence.*;
import lombok.*;
import net.lodgames.inquiry.constants.InquiryStatus;
import net.lodgames.inquiry.constants.InquiryType;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity(name ="inquiry")
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Inquiry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_id")
    private Long userId;             // 문의자 아이디
    private InquiryType type;        // 문의 종류     Payment(0),Access(1),Play(2),Feedback(3),Other(4)
    private String reason;           // 문의 내용
    private String screenshot;       // 스크린샷 저장 경로
    private InquiryStatus status;    // 문의 상태     PROGRESS(0),RESOLVE(1)
    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt; // 생성 시각
    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt; // 변경 시각

    @Builder
    public Inquiry(Long userId,
                   InquiryType type,
                   String reason,
                   String screenshot,
                   InquiryStatus status) {
        this.userId = userId;
        this.type = type;
        this.reason = reason;
        this.screenshot = screenshot;
        this.status = status;
    }
}
