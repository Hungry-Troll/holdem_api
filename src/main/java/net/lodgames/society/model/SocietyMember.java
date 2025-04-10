package net.lodgames.society.model;

import jakarta.persistence.*;
import lombok.*;
import net.lodgames.society.constants.MemberType;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity(name="society_member")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class SocietyMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                 // 멤버 고유번호
    private Long userId;             // 유저 고유번호
    private Long societyId;          // 모임 고유번호
    private MemberType memberType;   // 멤버 타입
    @CreatedDate
    private LocalDateTime createdAt; // 만든날짜
    @LastModifiedDate
    private LocalDateTime updatedAt; // 변경일
}
