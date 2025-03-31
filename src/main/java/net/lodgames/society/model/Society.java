package net.lodgames.society.model;

import jakarta.persistence.*;
import lombok.*;
import net.lodgames.society.constants.JoinType;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity(name="society")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Society {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;            // 모임 고유번호
    private String name;        // 모임 이름
    private JoinType joinType;  // 참가 방식
    private String passcode;    // 비밀번호
    private String image;       // 이미지 경로
    private String backImage;   // 배경 이미지 경로
    private String info;        // 소개
    private String tag;         // 태그
    @CreatedDate
    private LocalDateTime createdAt; // 만든날짜
    @LastModifiedDate
    private LocalDateTime updatedAt; // 변경일

}
