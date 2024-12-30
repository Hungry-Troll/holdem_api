package net.lodgames.dictionary.userCharacter.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity(name = "user_character")
@Data
@Builder
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
    private Long characterId;
    @Column(name = "customise_id")
    private Long customiseId;
    @Column(name = "level")
    private Integer level;
    @Column(name = "grade")
    private Integer grade;
    @Column(name = "status_index")
    private Integer statusIndex;
    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt; // 만든날짜
    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt; // 변경일
}
