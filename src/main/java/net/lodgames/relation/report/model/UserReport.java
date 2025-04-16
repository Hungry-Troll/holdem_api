package net.lodgames.relation.report.model;

import jakarta.persistence.*;
import lombok.*;
import net.lodgames.relation.report.constants.UserReportStatus;
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
    @Column(name = "reporter_id")
    private Long reporterId; // 신고자
    @Column(name = "target_user_id")
    private Long targetUserId; // 신고 대상
    private String reason; // 신고 사유
    private UserReportStatus status; // 신고 상태
    private String screenshot; // 스크린샷
    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt; // 신고 날짜
    @LastModifiedDate
    @Column(name = "updated_at")
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
