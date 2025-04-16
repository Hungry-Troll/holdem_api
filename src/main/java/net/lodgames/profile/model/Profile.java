package net.lodgames.profile.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity(name = "profile")
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_id")
    private Long userId;
    private String nickname;
    private String image;
    @Column(name = "basic_image_idx")
    private Integer basicImageIdx;
    @CreatedDate
    private LocalDateTime createdAt; // 만든날짜
    @LastModifiedDate
    private LocalDateTime updatedAt; // 변경일

    @Builder
    public Profile(Long userId, String nickname, String image, Integer basicImageIdx) {
        this.userId = userId;
        this.nickname = nickname;
        this.image = image;
        this.basicImageIdx = basicImageIdx;
    }
}
