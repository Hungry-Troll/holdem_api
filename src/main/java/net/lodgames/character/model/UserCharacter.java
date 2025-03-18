package net.lodgames.character.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity(name = "user_character")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class UserCharacter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "character_id")
    private long characterId;
    @Column(name = "customise_id")
    private long customiseId;
    private int level;
    private int grade;
    @Column(name = "status_index")
    private int statusIndex;
    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt; // 만든날짜
    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt; // 변경일

    @Builder
    public UserCharacter(Long userId,
                         long characterId,
                         long customiseId,
                         int level,
                         int grade,
                         int statusIndex) {
        this.userId = userId;
        this.characterId = characterId;
        this.customiseId = customiseId;
        this.level = level;
        this.grade = grade;
        this.statusIndex = statusIndex;
    }
}
