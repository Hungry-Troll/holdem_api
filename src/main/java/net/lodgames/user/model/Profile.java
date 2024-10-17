package net.lodgames.user.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@Entity(name = "profile")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "nickname")
    private String nickname;
    @Column(name = "unique_nickname")
    private String uniqueNickname;
    @Column(name = "image")
    private String image;
    @UpdateTimestamp
    private LocalDateTime updatedAt; // 변경일
    @CreationTimestamp
    private LocalDateTime createdAt; // 만든날짜

}
