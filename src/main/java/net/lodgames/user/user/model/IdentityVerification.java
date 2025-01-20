package net.lodgames.user.user.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class IdentityVerification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                 // 본인인증 고유번호
    private String ci;               // 연계정보 고유 아이디
    private Long userId;             // 유저 고유번호
    @CreatedDate
    private LocalDateTime createdAt; // 생성시각

    @Builder
    public IdentityVerification(String ci, Long userId) {
        this.ci = ci;
        this.userId = userId;
        this.createdAt = LocalDateTime.now();
    }
}