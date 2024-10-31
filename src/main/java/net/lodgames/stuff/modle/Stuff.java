package net.lodgames.stuff.modle;

import net.lodgames.stuff.constants.StuffStatus;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Data
@Entity(name="stuff")
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Stuff {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 물건 고유번호
    @Column(name = "status")
    private StuffStatus status; // 물건 상태
    @Column(name = "name")
    private String name; // 물건 이름
    @Column(name="description")
    private String description; // 물건 설명
    @Column(name="make_datetime")
    private LocalDateTime makeDatetime; // 물건 생산 날짜
    @LastModifiedDate
    private LocalDateTime updatedAt; // 변경일
    @CreatedDate
    private LocalDateTime createdAt; // 만든날짜

    @Builder
    public Stuff(StuffStatus status, String name, String description, LocalDateTime makeDatetime) {
        this.status = status;
        this.name = name;
        this.description = description;
        this.makeDatetime = makeDatetime;
    }
}
