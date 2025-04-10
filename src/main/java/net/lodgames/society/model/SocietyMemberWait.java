package net.lodgames.society.model;

import jakarta.persistence.*;
import lombok.*;
import net.lodgames.society.constants.WaitType;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Entity(name="society_member_wait")
@NoArgsConstructor
@Builder
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class SocietyMemberWait {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                // 멤버 고유번호
    private Long userId;            // 유저 고유번호
    private Long societyId;         // 모임 고유번호
    private WaitType waitType;      // 가입 타입
    @CreatedDate
    private LocalDateTime createdAt; // 만든날짜
}
