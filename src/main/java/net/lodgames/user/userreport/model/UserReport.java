package net.lodgames.user.userreport.model;

import jakarta.persistence.*;
import lombok.*;
import net.lodgames.user.userreport.constants.UserReportStatus;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity(name = "user_report")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
public class UserReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, name = "reporter_id")
    private Long reporterId; // 신고자
    @Column(nullable = false, name ="target_user_id")
    private Long targetUserId; // 신고 대상
    @Column(length = 500)
    private String reason; // 신고 사유
    @Column(nullable = false)
    private UserReportStatus status; // 신고 상태
    @Column
    private String screenshot; // 스크린샷
    @CreatedDate
    @Column(nullable = false, name = "created_at")
    private LocalDateTime createdAt; // 신고 날짜
    @LastModifiedDate
    @Column(nullable = false, name ="updated_at")
    private LocalDateTime updatedAt; // 변경 날짜

    @Builder
    public UserReport(Long reporterId,
                      Long targetUserId,
                      String reason,
                      UserReportStatus status,
                      String screenshot) {
        this.reporterId = reporterId;
        this.targetUserId = targetUserId;
        this.reason = reason;
        this.status = status;
        this.screenshot = screenshot;
    }
}
